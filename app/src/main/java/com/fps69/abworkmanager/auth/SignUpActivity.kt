package com.fps69.abworkmanager.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fps69.abworkmanager.databinding.AccountVerificationDialogBinding
import com.fps69.abworkmanager.databinding.ActivitySignUpBinding
import com.fps69.abworkmanager.dataclass.User
import com.fps69.abworkmanager.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.internal.Util

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    // For Set Image
    private var userImageUri: Uri? = null
    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        userImageUri = it
        binding.IVUplodeImage.setImageURI(userImageUri)
    }
    private var userType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            IVUplodeImage.setOnClickListener {
                selectImage.launch("image/*")
            }
            RGRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                userType = findViewById<RadioButton>(checkedId).text.toString()
                Log.d("Cheack", "${userType}")
            }
            btnSignUp.setOnClickListener {
                uplodeImageUrl()
            }
            tvSignIn.setOnClickListener {
                startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
                finish()
            }
        }


    }

    private fun uplodeImageUrl() {
        Utils.showDialog(this)

        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etPasswordConfirm.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            if (userImageUri == null) {
                Utils.hideDialog()
                Utils.showToast(this, " Please Select Image ")
            } else if (password == confirmPassword) {
                if (userType != "") {
                    uplodeUserData(name, email, password)
                } else {
                    Utils.hideDialog()
                    Utils.showToast(this@SignUpActivity, " Please Select User Type ")
                }

            } else {
                Utils.showToast(this, " Password and Confirm Password didn't match")
            }
        } else {
            Utils.hideDialog()

            Utils.showToast(this, " Enter all the Details before SignUp")
        }
    }

    private fun uplodeUserData(name: String, email: String, password: String) {
        lifecycleScope.launch {
            try {
                val firebaseAuth =
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .await()
                val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()
                val storageReference =
                    FirebaseStorage.getInstance().getReference("profile").child(currentUserId)
                        .child("profile.jpg")
                val uploadTask = storageReference.putFile(userImageUri!!).await()
                if (uploadTask.task.isSuccessful) {
                    val downlodeUrl = storageReference.downloadUrl.await()
                    saveUserData(name, email, password, downlodeUrl)
                } else {
                    Utils.hideDialog()
                    showToast("Upload failed : ${uploadTask.task.exception?.message}")
                }
            } catch (e: Exception) {
                Utils.hideDialog()
                showToast("Upload failed : ${e.message}")
            }
        }
    }

    private fun saveUserData(name: String, email: String, password: String, downlodeUrl: Uri?) {
        lifecycleScope.launch {
            val db = FirebaseDatabase.getInstance().getReference("User")


            try {
                val CurrentUser = FirebaseAuth.getInstance().currentUser
                if (CurrentUser != null) {
                    //For Email Verification
                    FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
                        ?.addOnSuccessListener {
                            val dialog =
                                AccountVerificationDialogBinding.inflate(LayoutInflater.from(this@SignUpActivity))
                            val alertDialog = AlertDialog.Builder(this@SignUpActivity)
                                .setView(dialog.root)
                                .create()
                            Utils.hideDialog()
                            alertDialog.show()
                            dialog.btnDialogOk.setOnClickListener {
                                alertDialog.dismiss()
                                startActivity(
                                    Intent(
                                        this@SignUpActivity,
                                        SignInActivity::class.java
                                    )
                                )
                                this@SignUpActivity.finish()
                            }
                        }
                    val uId = CurrentUser.uid.toString()
                    val saveUseType = if (userType == "Boss") "Boss" else "Employee"
                    val user = User(
                        userType = saveUseType,
                        userId = uId,
                        userName = name,
                        userEmail = email,
                        userPassword = password,
                        userImage = downlodeUrl.toString()
                    )
                    db.child(uId).setValue(user).await()
                    FirebaseAuth.getInstance().signOut()
                    Utils.showToast(this@SignUpActivity, "SignUp Successfully")

                } else {
                    Utils.hideDialog()
                    Utils.showToast(this@SignUpActivity, "SignUp Failed")
                }
            } catch (e: Exception) {
                Utils.hideDialog()
                Utils.showToast(this@SignUpActivity, "SignUp Failed :- ${e.message}")
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}