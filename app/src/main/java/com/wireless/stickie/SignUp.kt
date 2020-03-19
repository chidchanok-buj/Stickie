package com.wireless.stickie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.toast


//fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
//    Toast.makeText(this, message, duration).show()
//}

class SignUp : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private val TAG: String = "Sign Up Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        if (mAuth!!.currentUser != null) {
            // If yes,, go to main topic page
            startActivity(Intent(this@SignUp, MainTopic::class.java))
            finish()
        }

        signing_up_button.setOnClickListener {
//            See Logcat
//            Log.e("Something's broken","FIX IT")
            val firstName = first_name.text.toString().trim { it <= ' ' }
            val lastName = last_name.text.toString().trim { it <= ' ' }
            val email = email.text.toString().trim { it <= ' ' }
            val password = password_sign_up.text.toString().trim { it <= ' ' }

            if (firstName.isEmpty()) {
                toast("Please enter your first name.")
                Log.d(TAG, "First name was empty.")
                return@setOnClickListener
            }
            if (lastName.isEmpty()) {
                toast("Please enter your last name.")
                Log.d(TAG, "Last name was empty.")
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                toast("Please enter your email.")
                Log.d(TAG, "Email was empty.")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                toast("Please enter your password.")
                Log.d(TAG, "Password was empty.")
                return@setOnClickListener
            }

            mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    if (password.length < 6) {
                        toast("Password is too short. Please enter minimum 6 characters.")
                        Log.d(TAG, "Password was less than 6 characters.")
                    } else {
                        toast("Authentication Failed: " + task.exception)
                        Log.d(TAG, "Authentication Failed: " + task.exception)
                    }
                } else {
                    toast("Successfully create your account!")
                    Log.d(TAG, "Successfully create an account.")
                    startActivity(Intent(this@SignUp, MainTopic::class.java))
                }
            }
        }
//        log_in.setOnClickListener {
//            startActivity(Intent(this@SignUp, LoginActivity::class.java))
//        }
    }
//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = mAuth!!.currentUser
//        updateUI(currentUser)
//    }
}
