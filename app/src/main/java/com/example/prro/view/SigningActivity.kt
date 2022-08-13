package com.example.prro.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.prro.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signing.*

class SigningActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signing)

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

        if (currentUser != null) {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun signIn(view: View) {
        var email = emailText.text.toString()
        var password = passwordText.text.toString()

        if (email.isNotBlank() && password.isNotBlank()) {

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                }


            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(
                this,
                "Please do not leave empty E-mail and Password lines",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun signUp(view: View) {
        var email = emailText.text.toString()
        var password = passwordText.text.toString()

        if (email.isNotBlank() && password.isNotBlank()) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener() {
                if (it.isSuccessful) {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill in the blanks and create a user if you don't have an account", Toast.LENGTH_SHORT).show()
        }
    }
}


