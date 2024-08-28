package com.fps69.abworkmanager.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fps69.abworkmanager.BossActivity
import com.fps69.abworkmanager.EmployeeActivity
import com.fps69.abworkmanager.R
import com.fps69.abworkmanager.dataclass.User
import com.fps69.abworkmanager.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            val currentUSer = FirebaseAuth.getInstance().currentUser?.uid
            if(currentUSer!=null){
                lifecycleScope.launch {
                    try {
                        FirebaseDatabase.getInstance().getReference("User").child(currentUSer).addListenerForSingleValueEvent(object :ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val currentUserData= snapshot.getValue(User::class.java)
                                when(currentUserData?.userType){
                                    "Boss"->{
                                        startActivity(Intent(this@SplashScreenActivity,BossActivity::class.java))
                                        finish()
                                    }
                                    "Employee"->{
                                        startActivity(Intent(this@SplashScreenActivity,EmployeeActivity::class.java))
                                        finish()
                                    }
                                    else->{
                                        startActivity(Intent(this@SplashScreenActivity,EmployeeActivity::class.java))
                                        finish()
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Utils.hideDialog()
                                Utils.showToast(this@SplashScreenActivity,"Something went wrong${error.message}")
                            }

                        })
                    }catch (e:Exception){
                        Utils.showToast(this@SplashScreenActivity,"Something went wrong${e.message}")
                    }
                }
            }else{
                startActivity(Intent(this@SplashScreenActivity,SignInActivity::class.java))
                finish()
            }

        },5000)

    }
}