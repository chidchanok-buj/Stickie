package com.wireless.stickie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_arqs.*

class arqsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arqs)

        arqs_learning.setOnClickListener{
            val intent = Intent(this, arqs_learning_slidingwindow::class.java)
            startActivity(intent)
        }
    }
}
