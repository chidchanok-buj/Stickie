package com.wireless.stickie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_model_layer.*

class ModelLayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model_layer)

        model_layer_exercise.setOnClickListener {
            val intent = Intent(this, ShuffleOSI::class.java)
            startActivity(intent)
        }
    }
}
