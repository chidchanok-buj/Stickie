package com.wireless.stickie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_learning_image.*

class routing_rip : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learning_image)
        topic.text = getString(R.string.ripTopic)
        description_under_image.text = getString(R.string.ripDes)
        image.setImageResource(R.drawable.rip)
//        .setOnClickListener {
//            val intent = Intent(this, routing_learning::class.java)
//            startActivity(intent)
//        }
    }
}
