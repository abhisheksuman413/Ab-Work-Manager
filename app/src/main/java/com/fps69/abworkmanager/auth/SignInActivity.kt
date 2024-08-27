package com.fps69.abworkmanager.auth

import android.content.Intent
import android.icu.util.UniversalTimeScale
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.fps69.abworkmanager.BossActivity
import com.fps69.abworkmanager.EmployeeActivity
import com.fps69.abworkmanager.R
import com.fps69.abworkmanager.databinding.ActivitySignInBinding
import com.fps69.abworkmanager.dataclass.User
import com.fps69.abworkmanager.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.internal.Util

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            btnLogin.setOnClickListener {
                val email  = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                if(email.isNotEmpty()&&password.isNotEmpty()){
                    loginUser(email,password)
                }else{
                    Utils.hideDialog()
                    Utils.showToast(this@SignInActivity,"Please enter email and password")
                }
            }
            tvSignUp.setOnClickListener {
                startActivity(Intent(this@SignInActivity,SignUpActivity::class.java))
                finish()
            }
        }

    }

    private fun loginUser(email: String, password: String) {
        Utils.showDialog(this@SignInActivity)
        val firebaseAuth = FirebaseAuth.getInstance()
        lifecycleScope.launch {
            try {
                val authResult = firebaseAuth.signInWithEmailAndPassword(email,password).await()
                val currentUser = authResult.user?.uid
                if(currentUser!= null){
                    // Check user Boss Or Employee
                    FirebaseDatabase.getInstance().getReference("User").child(currentUser).addListenerForSingleValueEvent(object :ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val currentUserData = snapshot.getValue(User::class.java)
                            if (currentUserData != null) {
                                when(currentUserData.userType){
                                    "Boss"->{
                                        // Open Boss layout
                                        startActivity(Intent(this@SignInActivity,BossActivity::class.java))
                                        finish()
                                    }
                                    "Employee"->{
                                        // Open Employee layout
                                        startActivity(Intent(this@SignInActivity,EmployeeActivity::class.java))
                                        finish()
                                    }
                                    else->{
                                        //Open Employee layout
                                        startActivity(Intent(this@SignInActivity,BossActivity::class.java))
                                        finish()
                                    }
                                }
                            }
                            else{
                                Utils.showToast(this@SignInActivity,"Current user data is null")
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Utils.hideDialog()
                            Utils.showToast(this@SignInActivity, "Error due to ${error.message}")
                        }

                    })
                }
                else{
                    Utils.hideDialog()
                    Utils.showToast(this@SignInActivity, " User not found Please Sign Up first ")
                }
            }catch (e:Exception){
                Utils.hideDialog()
                Utils.showToast(this@SignInActivity, " Something went wrong :- ${e.message!!}")
            }
        }
    }
}