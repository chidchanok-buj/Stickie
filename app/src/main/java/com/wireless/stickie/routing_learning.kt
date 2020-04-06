package com.wireless.stickie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_learning.*
import kotlinx.android.synthetic.main.activity_learning.topic
import kotlinx.android.synthetic.main.activity_learning_image.*

class routing_learning : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learning_image)
        topic.text = getString(R.string.routing_protocols_topic)
        description_under_image.text = getString(R.string.routingDes)
        description_under_image2.text = getString(R.string.routingDes2)
        image.setImageResource(R.drawable.routing_protocol)
        next.setOnClickListener{
            val intent = Intent(this, routing_rip::class.java)
            startActivity(intent)
        }
    }
}