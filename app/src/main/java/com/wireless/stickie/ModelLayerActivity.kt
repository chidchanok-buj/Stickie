package com.wireless.stickie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.layout_topic.*

class ModelLayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_topic)

//        model_layer_learning.setOnClickListener {
//            val intent = Intent(this, ::class.java)
//            startActivity(intent)
//        }

        exercise.setOnClickListener {
            val intent = Intent(this, ShuffleLayer::class.java)
            startActivity(intent)
        }

    }

//    fun addLayer(model: String) {
//        layerItems?.add("Application Layer")
//
//        if (model == "OSI") {
//            layerItems?.add("Presentation Layer")
//            layerItems?.add("Session Layer")
//        }
//
//        layerItems?.add("Transport Layer")
//        layerItems?.add("Network Layer")
//        layerItems?.add("Data Link Layer")
//        layerItems?.add("Physical Layer")
//    }

}
