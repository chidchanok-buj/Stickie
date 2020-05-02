package com.wireless.stickie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wireless.stickie.Adapter.DragDropRecyclerAdapter
import kotlinx.android.synthetic.main.activity_shuffle_o_s_i.*


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

}
