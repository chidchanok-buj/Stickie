package com.wireless.stickie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.layout_topic.*
import java.util.*

class Learning : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_topic)

//        topic.text = getString(R.string.model_layer_topic)

        model_layer.setOnClickListener {
            val intent = Intent(this, ModelLearning::class.java)
            startActivity(intent)
        }

        arqs.setOnClickListener {
            val intent = Intent(this, ARQLearning::class.java)
            startActivity(intent)
        }

        routing.setOnClickListener{
            val intent = Intent(this, RoutingLearning::class.java)
            startActivity(intent)
        }

        congestion.setOnClickListener{
            val intent = Intent(this, CongestionLearning::class.java)
            startActivity(intent)
        }

    }

    private fun showChangeLang() {
        val listItems = arrayOf("ภาษาไทย","English")
        val mBuilder = AlertDialog.Builder(this@Learning)
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
