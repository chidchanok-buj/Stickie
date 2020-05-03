package com.wireless.stickie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_topic.*
import org.jetbrains.anko.toast
import java.util.*


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

        learning.setOnClickListener {
            val intent = Intent(this, Learning::class.java)
            startActivity(intent)
        }

        exercise.setOnClickListener {
            val intent = Intent(this, Exercise::class.java)
            startActivity(intent)
        }

        quiz.setOnClickListener {
            val intent = Intent(this, CategoryQuiz::class.java)
            startActivity(intent)
        }

        score.setOnClickListener {
            val intent = Intent(this, ScoreActivity::class.java)
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

    private fun showChangeLang() {
        val listItems = arrayOf("ภาษาไทย","English")
        val mBuilder = AlertDialog.Builder(this@MainTopic)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listItems, -1) { dialog, which ->
            if (which == 0) {
                setLocate("th")
                recreate()
            } else if (which == 1) {
                setLocate("en")
                recreate()
            }
            dialog.dismiss()
        }

        val mDialog = mBuilder.create()

        mDialog.show()
    }

    private fun setLocate(Lang: String?) {
        val config = resources.configuration
        val locale = Locale(Lang)

        Locale.setDefault(locale)
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)

        recreate()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_lang -> {
                showChangeLang()
            }
        }
        return true
    }

}
