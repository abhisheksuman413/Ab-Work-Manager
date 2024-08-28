package com.fps69.abworkmanager

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fps69.abworkmanager.auth.SignInActivity
import com.fps69.abworkmanager.databinding.ActivityEmployeeBinding
import com.google.firebase.auth.FirebaseAuth

class EmployeeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEmployeeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tbEmployeeAllWorks.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.logOut->{
                    // Show Logout Dialog here
                    showLogOutDialog()
                    true
                }
                else -> false
            }
        }

    }

    private fun showLogOutDialog() {
        val builder = AlertDialog.Builder(this@EmployeeActivity)
        val alertDialog = builder.create()
        builder.setTitle("Log Out")
            .setMessage("Are you sure you want to Log Out? ")
            .setPositiveButton("Yes"){_,_->
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@EmployeeActivity,SignInActivity::class.java))
                this@EmployeeActivity.finish()
            }
            .setNegativeButton("No"){_,_->
                alertDialog.dismiss()
            }
            .show()
            .setCancelable(false)
    }
}