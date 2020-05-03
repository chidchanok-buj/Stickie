package com.wireless.stickie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.wireless.stickie.Adapter.CategoryAdapter
import com.wireless.stickie.Common.SpacesItemDecoration
import com.wireless.stickie.DBHelper.DBHelper
import kotlinx.android.synthetic.main.activity_category_quiz.*
import java.util.*

class CategoryQuiz : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_quiz)

//        toolbar.title = "EDMT QUIZ 2019"
//        setSupportActionBar(toolbar)

        recycler_category.setHasFixedSize(true)
        recycler_category.layoutManager = GridLayoutManager(this, 2)

        val adapter = CategoryAdapter(this, DBHelper.getInstance(this).allCategories)
        recycler_category.addItemDecoration(SpacesItemDecoration(4))
        recycler_category.adapter = adapter
    }
    private fun showChangeLang() {
        val listItems = arrayOf("ภาษาไทย","English")
        val mBuilder = AlertDialog.Builder(this@CategoryQuiz)
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
