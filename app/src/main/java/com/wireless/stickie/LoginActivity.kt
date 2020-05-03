package com.wireless.stickie
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import java.util.*

class LoginActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    private val TAG: String = "Login Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        if (mAuth!!.currentUser != null) {
            startActivity(Intent(this@LoginActivity, MainTopic::class.java))
            finish()
        }

        login.setOnClickListener {
            val email = username.text.toString().trim { it <= ' ' }
            val password = password.text.toString().trim { it <= ' ' }

            if (email.isEmpty()) {
                toast("Please enter your email address.")
                Log.d(TAG, "Email was empty.")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                toast("Please enter your password.")
                Log.d(TAG, "Password was empty.")
                return@setOnClickListener
            }

            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    if (password.length < 6) {
                        toast("Please check your password. Password must have minimum 6 characters.")
                        Log.d(TAG, "Enter password less than 6 characters.")
                    } else {
                        toast("Authentication Failed: " + task.exception!!.message)
                        Log.d(TAG, "Authentication Failed: " + task.exception!!.message)
                    }
                } else {
                    toast("Sign in successfully!")
                    Log.d(TAG, "Sign in successfully!")
                    startActivity(Intent(this@LoginActivity, MainTopic::class.java))
                    finish()
                }
            }
        }

//        signup.setOnClickListener { startActivity(Intent(this@LoginActivity, SignUp::class.java)) }

//        login_backBtn.setOnClickListener { onBackPressed() }
    }

    private fun showChangeLang() {
        val listItems = arrayOf("ภาษาไทย","English")
        val mBuilder = AlertDialog.Builder(this@LoginActivity)
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