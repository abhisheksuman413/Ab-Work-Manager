package com.fps69.abworkmanager.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fps69.abworkmanager.R
import com.fps69.abworkmanager.databinding.FragmentWorksBinding

class WorksFragment : Fragment() {

    // Creating variable for employee Details
    val employeeDetail by navArgs<WorksFragmentArgs>()
    private lateinit var binding: FragmentWorksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentWorksBinding.inflate(layoutInflater)



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


}