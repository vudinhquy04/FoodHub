package com.quyvd.foodhub

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SplashScreenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        

        Handler(Looper.getMainLooper()).postDelayed({
            if (isUserLoggedIn()) {
                updateUI(user = FirebaseAuth.getInstance().currentUser)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                Log.d("SplashScreenActivity", "User is not logged in")
            }
        }, 5000)
    }

    private fun isUserLoggedIn(): Boolean {
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
//        if ( currentUser != null) {
//            Log.d("QUYVD","Email: ${currentUser.email}")
//            Log.d("QUYVD","Display Name: ${currentUser.displayName}")
//            Log.d("QUYVD","Photo Url : ${currentUser.photoUrl}")
//        }
        return currentUser != null
    }

    private fun updateUI(user: FirebaseUser?) {
        user?.let {
            Log.d("SplashScreenActivity", "User signed in: ${user.displayName}")
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("email", user.email)
                putExtra("displayName", user.displayName ?: "No Display Name")
                putExtra("photoUrl", user.photoUrl?.toString() ?: "No Photo URL")
            }
            startActivity(intent)
            finish()
        }
    }

}