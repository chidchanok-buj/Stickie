package com.wireless.stickie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_topic.*
import org.jetbrains.anko.toast

class MainTopic : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private val TAG: String = "Main Topic"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_topic)
        mAuth = FirebaseAuth.getInstance()

        val user = mAuth!!.currentUser

        displayName.text = user!!.displayName

        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val users = firebaseAuth.currentUser
            if (users == null) {
                startActivity(Intent(this@MainTopic, LoginActivity::class.java))
                finish()
            }
        }

        result_signOutBtn.setOnClickListener {
            mAuth!!.signOut()
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
