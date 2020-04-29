package com.wireless.stickie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wireless.stickie.ui.ScoreActivity
import kotlinx.android.synthetic.main.layout_topic.*

class RoutingProtocolsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_topic)
        topic.text = getString(R.string.routing_protocols_topic)
        learning.setOnClickListener{
            val intent = Intent(this, ScoreActivity::class.java)
            startActivity(intent)
        }
        quiz.setOnClickListener{
            val intent = Intent(this, CategoryQuiz::class.java)
            startActivity(intent)
        }
    }
}
