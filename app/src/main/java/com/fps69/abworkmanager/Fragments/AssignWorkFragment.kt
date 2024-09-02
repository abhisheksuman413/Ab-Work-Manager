package com.fps69.abworkmanager.Fragments

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fps69.abworkmanager.R
import com.fps69.abworkmanager.databinding.FragmentAssignWorkBinding
import com.fps69.abworkmanager.dataclass.Works
import com.fps69.abworkmanager.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.Locale


class AssignWorkFragment : Fragment() {

    // Creating variable for employee Details
    val employeeData by navArgs<AssignWorkFragmentArgs>()

    private lateinit var binding: FragmentAssignWorkBinding
    private var priority = "1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAssignWorkBinding.inflate(layoutInflater)


        // This Function is for set priority
        setPriority()
        // This Function is for set date
        setDate()

        binding.apply {
            tbAssineWork.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
            btnDone.setOnClickListener {
                // This Function is for assign work
                assignWork()
            }
        }

        return binding.root
    }

    private fun assignWork() {
        Utils.showDialog(requireContext())

        val workTitle = binding.etTitle.text.toString()
        val workDescription = binding.etWorkDescription.text.toString()
        val workLastDate = binding.tvDate.text.toString()

        if (workTitle.isEmpty()) {
            Utils.apply {
                hideDialog()
                showToast(requireContext(), "Please Enter Work Titles")
            }
        } else if (workLastDate == "Last Date : ")
            Utils.apply {
                hideDialog()
                showToast(requireContext(), "Please Select Last Date")
            }
        else {
            val employeeId = employeeData.employeeDetail.userId
            val bossId = FirebaseAuth.getInstance().currentUser?.uid
            val workRoom = bossId + employeeId

            val work = Works(
                workTitle = workTitle,
                workDesc = workDescription,
                workPriority = priority,
                workLastDate = workLastDate,
                workStatus = "1"
            )

            FirebaseDatabase.getInstance().getReference("Works").child(workRoom).push().setValue(work)
                // Yha push()eak random id generate krta hai same employee ko alg alg work assign ho isliye iska use krte hai
                .addOnSuccessListener {
                    Utils.apply {
                        hideDialog()
                        showToast(requireContext(),"Work has been assigned to : ${employeeData.employeeDetail.userName}")
                        val action = AssignWorkFragmentDirections.actionAssignWorkFragmentToWorksFragment(employeeData.employeeDetail)
                        findNavController().navigate(action)
                    }
                }
        }

    }

    private fun setDate() {

        val myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalender.apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLable(myCalender)
            }
        }
        binding.dateSelector.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                datePicker,
                myCalender.get(Calendar.YEAR),
                myCalender.get(Calendar.MONTH),
                myCalender.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateLable(myCalender: Calendar?) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.tvDate.text = sdf.format(myCalender?.time)
    }

    private fun setPriority() {
        binding.apply {
            ivGreenOval.setOnClickListener {
                Utils.showToast(requireContext(), "priority : Low")
                priority = "1"
                binding.ivGreenOval.setImageResource(R.drawable.done_icon)
                binding.ivYellowOval.setImageResource(0)
                binding.ivRedOval.setImageResource(0)
            }
            ivYellowOval.setOnClickListener {
                Utils.showToast(requireContext(), "priority : Medium")
                priority = "2"
                binding.ivYellowOval.setImageResource(R.drawable.done_icon)
                binding.ivGreenOval.setImageResource(0)
                binding.ivRedOval.setImageResource(0)
            }
            ivRedOval.setOnClickListener {
                Utils.showToast(requireContext(), "priority : High")
                priority = "3"
                binding.ivRedOval.setImageResource(R.drawable.done_icon)
                binding.ivGreenOval.setImageResource(0)
                binding.ivYellowOval.setImageResource(0)
            }
        }
    }


}