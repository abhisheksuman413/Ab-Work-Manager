package com.fps69.abworkmanager.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.fps69.abworkmanager.R
import com.fps69.abworkmanager.auth.SignInActivity
import com.fps69.abworkmanager.databinding.FragmentEmployeesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class EmployeesFragment : Fragment() {

    private lateinit var binding: FragmentEmployeesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentEmployeesBinding.inflate(layoutInflater)

        binding.tbEmployees.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.logOut->{
                    showLogoutDialog()
                    true
                }
                else -> false
            }
        }

        return binding.root
    }

    private fun showLogoutDialog(){
        val builder = AlertDialog.Builder(requireContext())
        val alertDialog = builder.create()
        builder.setTitle("Log Out")
            .setMessage("Are you sure you want to Log Out? ")
            .setPositiveButton("Yes"){_,_->
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(requireContext(),SignInActivity::class.java))
                requireContext().fileList()
            }
            .setNegativeButton("No"){_,_->
                alertDialog.dismiss()
            }
            .show()
            .setCancelable(false)

    }


}