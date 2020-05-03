package com.wireless.stickie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_topic.*
import org.jetbrains.anko.toast


class MainTopic : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private val TAG: String = "Main Topic"
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_topic)
        mAuth = FirebaseAuth.getInstance()
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            displayName.text = acct.displayName
        } else {
            val user = mAuth!!.currentUser
            if (user != null) {
                displayName.text = user.displayName
            } else {
                toast("Log in as Guest")
            }
        }


        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val users = firebaseAuth.currentUser
            if (users == null) {
                startActivity(Intent(this@MainTopic, LoginActivity::class.java))
                finish()
            }
        }

        model_layer.setOnClickListener {
            val intent = Intent(this, ModelLayerActivity::class.java)
            startActivity(intent)
        }

        arqs.setOnClickListener {
            val intent = Intent(this, arqsActivity::class.java)
            startActivity(intent)
        }

        routing.setOnClickListener {
            val intent = Intent(this, RoutingProtocolsActivity::class.java)
            startActivity(intent)
        }

        congestion.setOnClickListener {
            val intent = Intent(this, CongestionControlActivity::class.java)
            startActivity(intent)
        }

        result_signOutBtn.setOnClickListener {
            mAuth!!.signOut()
            mGoogleSignInClient.signOut()
            toast("Signed Out")
            Log.d(TAG, "Signed Out")
            startActivity(Intent(this@MainTopic, MainActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener { mAuthListener }
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth!!.removeAuthStateListener { mAuthListener }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true)
        }
        return super.onKeyDown(keyCode, event)
    }

}
