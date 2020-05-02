package com.wireless.stickie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.layout_topic.*

class CongestionControlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_topic)

        topic.text = getString(R.string.congestion_control_topic)

        learning.setOnClickListener{
            val intent = Intent(this, CongestionLearning::class.java)
            startActivity(intent)
        }

        exercise.setOnClickListener {
            val intent = Intent(this, CongestionExercise::class.java)
            startActivity(intent)
        }

        quiz.setOnClickListener{
            val intent = Intent(this, CategoryQuiz::class.java)
            startActivity(intent)
        }

        score.setOnClickListener{
            val intent = Intent(this, ScoreActivity::class.java)
            startActivity(intent)
        }
    }
}
