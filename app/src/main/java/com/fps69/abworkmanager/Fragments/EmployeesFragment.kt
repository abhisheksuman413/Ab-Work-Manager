package com.fps69.abworkmanager.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fps69.abworkmanager.Adapter.EmployeeListAdapter
import com.fps69.abworkmanager.R
import com.fps69.abworkmanager.auth.SignInActivity
import com.fps69.abworkmanager.databinding.FragmentEmployeesBinding
import com.fps69.abworkmanager.dataclass.User
import com.fps69.abworkmanager.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class EmployeesFragment : Fragment() {

    private lateinit var binding: FragmentEmployeesBinding
    private lateinit var employeeListAdapter: EmployeeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEmployeesBinding.inflate(layoutInflater)




        binding.apply {
            tbEmployees.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.logOut -> {
                        showLogoutDialogForLogOut()
                        true
                    }

                    else -> false
                }
            }


        }

        prepareRecyclerViewForEmployeeListAdapter()
        showAllEmployeeList()

        return binding.root
    }

    // This function is for fetch employee list from firebase
    private fun showAllEmployeeList() {
        Utils.showDialog(requireContext())
        FirebaseDatabase.getInstance().getReference("User")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val EmployeeList = arrayListOf<User>()
                    for (employee in snapshot.children) {
                        val currentUser = employee.getValue(User::class.java)
                        if (currentUser?.userType == "Employee") {
                            EmployeeList.add(currentUser)
                        }
                    }
                    employeeListAdapter.differ.submitList(EmployeeList)  // Yha adapter me data pass kr rhe hai
                    Utils.hideDialog()
                }

                override fun onCancelled(error: DatabaseError) {
                    Utils.apply {
                        hideDialog()
                        showToast(requireContext(), "Something went wrong ${error.message}")
                    }
                }

            })
    }


    // This function is for prepare recycler view for employee list adapter
    private fun prepareRecyclerViewForEmployeeListAdapter() {
        employeeListAdapter = EmployeeListAdapter()
        binding.rvEmployeeList.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = employeeListAdapter
        }
    }


    private fun showLogoutDialogForLogOut() {
        val builder = AlertDialog.Builder(requireContext())
        val alertDialog = builder.create()
        builder.setTitle("Log Out")
            .setMessage("Are you sure you want to Log Out? ")
            .setPositiveButton("Yes") { _, _ ->
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(requireContext(), SignInActivity::class.java))
                requireContext().fileList()
            }
            .setNegativeButton("No") { _, _ ->
                alertDialog.dismiss()
            }
            .show()
            .setCancelable(false)

    }


}