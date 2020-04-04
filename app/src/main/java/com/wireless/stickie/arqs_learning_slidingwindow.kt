package com.wireless.stickie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_learning.*


class arqs_learning_slidingwindow : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.wireless.stickie.R.layout.activity_learning)

        topic.text = getString(com.wireless.stickie.R.string.arqs_topic)
        description.text =
            getString(com.wireless.stickie.R.string.tab) + getString(com.wireless.stickie.R.string.tab) + getString(com.wireless.stickie.R.string.whatIsARQs)

        button.text = getString(com.wireless.stickie.R.string.next)

        button.setOnClickListener{
            val intent = Intent(this, arqs_learning_slidingwindow_2::class.java)
            startActivity(intent)
        }

    }
}
