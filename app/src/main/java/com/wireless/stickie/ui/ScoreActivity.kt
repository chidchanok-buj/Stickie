package com.wireless.stickie.ui

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.wireless.stickie.DBHelper.ScoreDBHelper
import com.wireless.stickie.R

class ScoreActivity : AppCompatActivity() {

    private var listView: ListView? = null
    private var scores: ArrayList<Int>? = null
    private var aa: ArrayAdapter<Int>? = null
    private var scoreDBHelper: ScoreDBHelper? = null
    private var cursor: Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        listView = findViewById(R.id.ListView)
        scores = ArrayList()
        aa = ArrayAdapter(this, android.R.layout.simple_list_item_1, scores!!)
        listView!!.setAdapter(aa)

        scoreDBHelper = ScoreDBHelper(this)
        scoreDBHelper!!.open()
        populateScore()
    }
    private fun populateScore() {
        cursor = scoreDBHelper!!.selectAllScore()
        updateArray()
    }
    private fun updateArray() {
        cursor!!.requery()
        scores!!.clear()
        if (cursor!!.moveToFirst()) {
            do {
                val score = cursor!!.getInt(cursor!!.getColumnIndex(ScoreDBHelper.KEY_SCORE))
                scores!!.add(0, score)
                aa!!.notifyDataSetChanged()
            } while (cursor!!.moveToNext())
        }
    }
}
