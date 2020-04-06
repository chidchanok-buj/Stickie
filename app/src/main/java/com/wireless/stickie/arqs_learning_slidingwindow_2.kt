package com.wireless.stickie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_learning_image.topic
import kotlinx.android.synthetic.main.activity_learning_image.back
import kotlinx.android.synthetic.main.activity_learning_image.*

class arqs_learning_slidingwindow_2 : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learning_image)

        topic.text = getString(R.string.flowControl)
        description_under_image.text = getString(R.string.flowControlDes)
        description_under_image2.text = getString(R.string.slidingWindowConcept)

        back.setOnClickListener{
            val intent = Intent(this, arqs_learning_slidingwindow::class.java)
            startActivity(intent)
        }
//        next.setOnClickListener{
//            val intent = Intent(this, arqs_learning_slidingwindow::class.java)
//            startActivity(intent)
//        }

    }
}