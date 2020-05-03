package com.wireless.stickie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wireless.stickie.Adapter.DragDropRecyclerAdapter
import kotlinx.android.synthetic.main.activity_shuffle_o_s_i.*
import java.util.*


class ModelExercise : AppCompatActivity(), OnStartDragListener {

    lateinit var adapter: DragDropRecyclerAdapter
    lateinit var touchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shuffle_o_s_i)

        adapter = DragDropRecyclerAdapter(this)
        populateListItem()
        val callback: ItemTouchHelper.Callback = ItemMoveCallbackListener(adapter)
        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        solution.setOnClickListener {
            val mAlertDialog = AlertDialog.Builder(this@ModelExercise)
            mAlertDialog.setIcon(R.mipmap.ic_launcher_round) // set alertdialog icon
            mAlertDialog.setTitle("Solution") // set alertdialog title
            mAlertDialog.setMessage("TCP/IP Model Layers") // set alertdialog message
            mAlertDialog.setView(R.layout.alert_solution);
            mAlertDialog.setPositiveButton("OK") { dialog, id ->
                dialog.dismiss()
            }
            mAlertDialog.show()
        }
    }

    private fun populateListItem() {
        val layers = listOf(
            "Physical Layer",
            "Transport Layer",
            "Data Link Layer",
            "Application Layer",
            "Network Layer"
        )
        adapter.setLayers(layers)
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        touchHelper.startDrag(viewHolder!!)
    }

    private fun showChangeLang() {
        val listItems = arrayOf("ภาษาไทย","English")
        val mBuilder = AlertDialog.Builder(this@ModelExercise)
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
