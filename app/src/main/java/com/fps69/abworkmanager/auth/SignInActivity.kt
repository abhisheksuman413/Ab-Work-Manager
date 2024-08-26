package com.fps69.abworkmanager.auth

import android.icu.util.UniversalTimeScale
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.fps69.abworkmanager.R
import com.fps69.abworkmanager.databinding.ActivitySignInBinding
import com.fps69.abworkmanager.utils.Utils
import com.google.firebase.auth.FirebaseAuth
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
            btnSignUp.setOnClickListener {
                val email  = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                if(email.isNotEmpty()&&password.isNotEmpty()){
                    loginUser(email,password)
                }else{
                    Utils.hideDialog()
                    Utils.showToast(this@SignInActivity,"Please enter email and password")
                }
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

                }
                else{
                    Utils.hideDialog()
                    Utils.showToast(this@SignInActivity, " User not found Please Sign Up first ")
                }
            }catch (e:Exception){
                Utils.hideDialog()
                Utils.showToast(this@SignInActivity, " Something went wrong :- ${e.message}")
            }
        }
    }
}