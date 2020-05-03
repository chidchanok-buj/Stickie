package com.wireless.stickie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.toast
import java.util.*


//fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
//    Toast.makeText(this, message, duration).show()
//}

class SignUp : AppCompatActivity() {

    lateinit var providers: List<AuthUI.IdpConfig>
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var gso: GoogleSignInOptions
    val RC_SIGN_IN: Int = 1

    //    lateinit var member : TextView
    private var mAuth: FirebaseAuth? = null
    private val TAG: String = "Sign Up Activity"
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        // Initialize Firebase Auth
        providers = Arrays.asList<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signIn = findViewById<View>(R.id.sign_in_google) as SignInButton
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        signIn.setOnClickListener { view: View? ->
            signInGoogle()
        }

//        showSignInOptions()
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

//            member = findViewById(R.id.already_member)
//
//            member.setOnClickListener {
//                startActivity(Intent(this@SignUp, LoginActivity::class.java))
//            }

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
                    val user = mAuth!!.currentUser
                    val profile =
                        UserProfileChangeRequest.Builder().setDisplayName(firstName).build()
                    user!!.updateProfile(profile)
//                    var user = mAuth!!.currentUser
//                    val profile = UserProfileChangeRequest.Builder().setDisplayName(firstName).build()
//                    user!!.updateProfile(profile)
                    startActivity(Intent(this@SignUp, MainTopic::class.java))
                }
            }
        }


//        log_in.setOnClickListener {
//            startActivity(Intent(this@SignUp, LoginActivity::class.java))
//        }
    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = mAuth!!.currentUser
//        updateUI(currentUser)
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            startActivityForResult(Intent(this@SignUp, MainTopic::class.java),RC_SIGN_IN)

//            handleResult(task)
//            val response = IdpResponse.fromResultIntent(data)
//            if (resultCode == Activity.RESULT_OK) {
//                val user = FirebaseAuth.getInstance().currentUser
//                Toast.makeText(this, "" + user!!.email, Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "" + response!!.error!!.message, Toast.LENGTH_SHORT).show()
//            }
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)!!
//            updateUI(account)
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

//    private fun showSignInOptions() {
//        startActivityForResult(
//            AuthUI.getInstance().createSignInIntentBuilder()
//                .setAvailableProviders(providers)
//                .setTheme(R.style.AppTheme).build(), MY_REQUEST_CODE
//        )
//
//    }
}
