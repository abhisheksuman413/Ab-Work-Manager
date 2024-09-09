package com.fps69.abworkmanager


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fps69.abworkmanager.Adapter.EmployeeActivityAllWorksAdapter
import com.fps69.abworkmanager.Api.ApiUtilities
import com.fps69.abworkmanager.auth.SignInActivity
import com.fps69.abworkmanager.databinding.ActivityEmployeeBinding
import com.fps69.abworkmanager.dataclass.FcmMessage
import com.fps69.abworkmanager.dataclass.Message
import com.fps69.abworkmanager.dataclass.NotificationContent
import com.fps69.abworkmanager.dataclass.User
import com.fps69.abworkmanager.dataclass.Works
import com.fps69.abworkmanager.utils.AccessToken
import com.fps69.abworkmanager.utils.Utils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.button.MaterialButton

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeBinding

    private lateinit var employeeActivityAllWorksAdapter: EmployeeActivityAllWorksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerViewForEmployeeActivityAllWorksAdapter()
        showEmployeeAllWorks()

        binding.apply {
            tbEmployeeAllWorks.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.logOut -> {
                        // Show Logout Dialog here
                        showLogOutDialog()
                        true
                    }

                    else -> false
                }
            }
        }

    }

    private fun showEmployeeAllWorks() {
        Utils.showDialog(this@EmployeeActivity)
        val empId = FirebaseAuth.getInstance().currentUser?.uid
        val workRef = FirebaseDatabase.getInstance().getReference("Works")
        workRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val workList = ArrayList<Works>()
                if (snapshot.exists()) {
                    binding.tvText.visibility = View.GONE
                    for (worksRooms in snapshot.children) {
                        if (worksRooms.key?.contains(empId!!) == true) {
                            val employeeWorkRef = workRef.child(worksRooms.key!!)
                            if (worksRooms.key!!.isEmpty()) {
                                Utils.hideDialog()
                                binding.tvText.visibility = View.VISIBLE
                            } else {
                                binding.tvText.visibility = View.GONE
                                employeeWorkRef.addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {

                                        for (allWorks in snapshot.children) {
                                            val works = allWorks.getValue(Works::class.java)
                                            if (works != null) {
                                                workList.add(works)
                                            }
                                        }
                                        employeeActivityAllWorksAdapter.differ.submitList(workList)
                                        Utils.hideDialog()
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        Utils.hideDialog()
                                        Utils.showToast(
                                            this@EmployeeActivity,
                                            "Something went wrong ${error.message}"
                                        )
                                    }

                                })
                            }

                        }
                    }
                } else {
                    Utils.hideDialog()
                    binding.tvText.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Utils.hideDialog()
                Utils.showToast(this@EmployeeActivity, "Something went wrong ${error.message}")
            }

        })
    }

    private fun prepareRecyclerViewForEmployeeActivityAllWorksAdapter() {
        employeeActivityAllWorksAdapter =
            EmployeeActivityAllWorksAdapter(::onCompletedButtonClicked, ::onStartingButtonClicked)
        binding.rvEmployeeAllWorks.apply {
            layoutManager =
                LinearLayoutManager(this@EmployeeActivity, LinearLayoutManager.VERTICAL, false)
            adapter = employeeActivityAllWorksAdapter
        }
    }

    private fun showLogOutDialog() {
        val builder = AlertDialog.Builder(this@EmployeeActivity)
        val alertDialog = builder.create()
        builder.setTitle("Log Out")
            .setMessage("Are you sure you want to Log Out? ")
            .setPositiveButton("Yes") { _, _ ->
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@EmployeeActivity, SignInActivity::class.java))
                this@EmployeeActivity.finish()
            }
            .setNegativeButton("No") { _, _ ->
                alertDialog.dismiss()
            }
            .show()
            .setCancelable(false)
    }

    fun onStartingButtonClicked(works: Works, startingButton: MaterialButton) {
        if (startingButton.text == "In Progress" && works.workStatus == "2") {
            Utils.showToast(
                this@EmployeeActivity,
                " Work Already In Progress \n If work is finish Please Mark as completed"
            )
        } else if (startingButton.text == "In Progress" && works.workStatus == "3") {
            Utils.showToast(this@EmployeeActivity, " Work Is Completed")
        } else {
            val builder = AlertDialog.Builder(this@EmployeeActivity)
            val alertDialog = builder.create()
            builder.setTitle("Starting Work ")
                .setMessage("Are you Starting this work ")
                .setPositiveButton("Yes") { _, _ ->
                    updateWorkStatus(works, "2")
                }
                .setNegativeButton("No") { _, _ ->
                    alertDialog.dismiss()
                }
                .show()
                .setCancelable(false)
        }
    }

    private fun updateWorkStatus(works: Works, status: String) {

        val empId = FirebaseAuth.getInstance().currentUser?.uid
        val workRef = FirebaseDatabase.getInstance().getReference("Works")
        workRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (workRooms in snapshot.children) {
                    if (workRooms.key?.contains(empId!!) == true) {
                        val employeeWorkRef = workRef.child(workRooms.key!!)
                        employeeWorkRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (allWork in snapshot.children) {
                                    val empWork = allWork.getValue(Works::class.java)
                                    if (empWork?.workId == works.workId) {
                                        employeeWorkRef.child(allWork.key!!).child("workStatus")
                                            .setValue(status)
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Utils.showToast(
                                    this@EmployeeActivity,
                                    "Something went wrong ${error.message}"
                                )
                            }

                        })
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Utils.showToast(this@EmployeeActivity, "Something went wrong ${error.message}")
            }

        })
    }

    fun onCompletedButtonClicked(works: Works, completedButton: MaterialButton) {
        if (completedButton.text == "Work Completed") {
            Utils.showToast(this@EmployeeActivity, " Work Is Completed")
        } else if (works.workStatus == "1") {
            Utils.showToast(this@EmployeeActivity, "Work not started yet Please start work")
        } else {
            val builder = AlertDialog.Builder(this@EmployeeActivity)
            val alertDialog = builder.create()
            builder.setTitle("Completed Work")
                .setMessage("Have you Completed this work ")
                .setPositiveButton("Yes") { _, _ ->
                    updateWorkStatus(works, "3")
                    initAuthReceiver(works.bossId, works.workTitle.toString())
                }
                .setNegativeButton("No") { _, _ ->
                    alertDialog.dismiss()
                }
                .show()
                .setCancelable(false)
        }

    }

    private fun initAuthReceiver(bossID: String?, workTitle: String) {
        val accessToken = AccessToken()
        lifecycleScope.launch(Dispatchers.IO) {
            val oathToken = accessToken.getAccessToken()
            Log.d("Suman", "AssineWork fragment : ${oathToken}")
            if (oathToken != null) {
                val authHeader = "Bearer $oathToken"
                sendNotification(bossID, workTitle, authHeader)
            }
        }
    }


    private fun sendNotification(bossID: String?, workTitle: String,authHeader:String) {
        val bossDataSnapshot = FirebaseDatabase.getInstance().getReference("User").child(bossID!!).get()
     bossDataSnapshot.addOnSuccessListener {
            val bossData = it.getValue(User::class.java)
            val bossToken = bossData?.userToken

            val notificationContent  = NotificationContent("Work Completed", workTitle)
            val message = Message(
                token = bossToken,
                notification = notificationContent,
                data = mapOf("title" to "Work Completed", "body" to workTitle)
            )

            val fcmMessage = FcmMessage(message)


            val apiCall= ApiUtilities.api.sendNotification(authHeader,fcmMessage)

            apiCall.enqueue(object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    if(response.isSuccessful){
                        Log.d("Suman","Notification sent")
                    } else {
                        Log.e("Suman", "Notification send failed: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("Suman", "Failed to send notification: ${t.message}")
                }

            })
        }

    }


}