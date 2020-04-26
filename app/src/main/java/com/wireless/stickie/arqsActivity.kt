package com.wireless.stickie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.layout_topic.*

class arqsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_topic)

        topic.text = getString(R.string.arqs_topic)

        learning.setOnClickListener{
            val intent = Intent(this, arqs_learning_slidingwindow::class.java)
            startActivity(intent)
        }
    }
}
