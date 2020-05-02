package com.wireless.stickie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.layout_topic.*

class ModelLayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_topic)

        topic.text = getString(R.string.model_layer_topic)

        learning.setOnClickListener {
            val intent = Intent(this, ModelLearning::class.java)
            startActivity(intent)
        }

        exercise.setOnClickListener {
            val intent = Intent(this, ModelExercise::class.java)
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
