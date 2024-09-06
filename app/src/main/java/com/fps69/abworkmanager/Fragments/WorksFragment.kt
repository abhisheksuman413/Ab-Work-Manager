package com.fps69.abworkmanager.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.fps69.abworkmanager.Adapter.WorksAdapter
import com.fps69.abworkmanager.databinding.FragmentWorksBinding
import com.fps69.abworkmanager.dataclass.Works
import com.fps69.abworkmanager.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import okhttp3.internal.Util

class  WorksFragment : Fragment() {

    // Creating variable for employee Details
    val employeeDetail by navArgs<WorksFragmentArgs>()

    private lateinit var worksAdapter:WorksAdapter

    private lateinit var binding: FragmentWorksBinding

    private lateinit var workRoom :String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWorksBinding.inflate(layoutInflater)

        prepareRecyclerViewForWorksAdapter()
        showAllWorks()



        // Fetching Employee Name
        val employeeName = employeeDetail.employeeData.userName

        binding.apply {
            tbEmployeeAllWorks.apply {
                title = employeeName
                setNavigationOnClickListener {
                    activity?.onBackPressed()
                }
            }
            btnAssignWork.setOnClickListener {
                val action = WorksFragmentDirections.actionWorksFragmentToAssignWorkFragment(employeeDetail.employeeData)
                findNavController().navigate(action)
            }
        }

        return binding.root
    }

    private fun showAllWorks() {
        Utils.showDialog(requireContext())
        val bossId = FirebaseAuth.getInstance().currentUser?.uid
        workRoom = bossId + employeeDetail.employeeData.userId
        FirebaseDatabase.getInstance().getReference("Works").child(workRoom)
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        binding.tvText.visibility= View.GONE
                        val workList = ArrayList<Works>()
                        for(allWorks in snapshot.children){
                            val work = allWorks.getValue(Works::class.java)
                            Log.d("WorksFragment", "Retrieved work: $work")
                            workList.add(work!!)
                        }
                        worksAdapter.differ.submitList(workList)
                        Utils.hideDialog()
                    }else{
                        Utils.hideDialog()
                        binding.tvText.visibility= View.VISIBLE
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Utils.hideDialog()
                    Utils.showToast(requireContext(),"Something went wrong ${error.message}")
                }

            })
    }

    private fun prepareRecyclerViewForWorksAdapter() {
        worksAdapter= WorksAdapter(::onUnassignedButtonClicked)
        binding.rvWork.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter=worksAdapter
        }
    }



    fun onUnassignedButtonClicked(works:Works){
        val builder = AlertDialog.Builder(requireContext())
        val alertDialog = builder.create()
        builder.setTitle("Unassigned Work")
            .setMessage("Are you sure you want to Unassigned Work? ")
            .setPositiveButton("Yes"){_,_->
                unassignWork(works)
            }
            .setNegativeButton("No"){_,_->
                alertDialog.dismiss()
            }
            .show()
            .setCancelable(false)
    }

    private fun unassignWork(works: Works) {
        works.expanded =! works.expanded // Ye kiye because av jis work pe click kiye hai uss work ka expanded true hai or Database me sare expanded false hai isliye
        FirebaseDatabase.getInstance().getReference("Works").child(workRoom)
            .addValueEventListener(object  :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(allWork in snapshot.children){
                        val currentWork = allWork.getValue(Works::class.java)
                        if(currentWork == works){
                            allWork.ref.removeValue()
                            Utils.showToast(requireContext(),"${currentWork.workTitle} :- Work has been Unassigned")
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Utils.hideDialog()
                    Utils.showToast(requireContext(),"Something went wrong ${error.message}")
                }

            })
    }
}