package com.wireless.stickie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_arqs.*

class RoutingProtocolsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arqs)
        topic.text = getString(R.string.routing_protocols_topic)
        arqs_learning.setOnClickListener{
            val intent = Intent(this, routing_learning::class.java)
            startActivity(intent)
        }
        arqs_quiz.setOnClickListener{
            val intent = Intent(this, CategoryQuiz::class.java)
            startActivity(intent)
        }
    }
}
