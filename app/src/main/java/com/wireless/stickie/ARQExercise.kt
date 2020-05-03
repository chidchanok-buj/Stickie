package com.wireless.stickie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_a_r_q_exercise.*
import java.util.*

class ARQExercise : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_r_q_exercise)

        arqSolution.setOnClickListener {
            val mAlertDialog = AlertDialog.Builder(this@ARQExercise)
            mAlertDialog.setIcon(R.mipmap.ic_launcher_round) // set alertdialog icon
            mAlertDialog.setTitle(getString(R.string.solution)) // set alertdialog title
            mAlertDialog.setMessage(getString(R.string.arqSolution)) // set alertdialog message
            mAlertDialog.setPositiveButton("OK") { dialog, id ->
                dialog.dismiss()
            }
            mAlertDialog.show()
        }
    }
    private fun showChangeLang() {
        val listItems = arrayOf("ภาษาไทย", "English")
        val mBuilder = AlertDialog.Builder(this@ARQExercise)
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
