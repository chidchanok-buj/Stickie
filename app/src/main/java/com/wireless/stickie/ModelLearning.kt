package com.wireless.stickie

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.wireless.stickie.Adapter.CardAdapter
import com.wireless.stickie.Model.ModelCard
import java.util.*
import kotlin.collections.ArrayList

class ModelLearning : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    var adapter: CardAdapter? = null
    lateinit var modelCards: ArrayList<ModelCard>
    var colors: Array<Int>? = null
    var argbEvaluator = ArgbEvaluator()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_view)
        modelCards = ArrayList()
        modelCards.add(ModelCard(R.drawable.tcplayer, getString(R.string.model_layer_topic), ""))
        modelCards.add(
            ModelCard(
                R.drawable.physical,
                getString(R.string.physical),
                getString(R.string.physicalDesc)
            )
        )
        modelCards.add(
            ModelCard(
                R.drawable.data,
                getString(R.string.data),
                getString(R.string.dataDesc)
            )
        )
        modelCards.add(
            ModelCard(
                R.drawable.network,
                getString(R.string.network),
                getString(R.string.networkDesc)
            )
        )
        modelCards.add(
            ModelCard(
                R.drawable.transport,
                getString(R.string.transport),
                getString(R.string.transportDesc)
            )
        )
        modelCards.add(
            ModelCard(
                R.drawable.application,
                getString(R.string.application),
                getString(R.string.applicationDesc)
            )
        )

        adapter = CardAdapter(modelCards, this)

        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = adapter
        viewPager.setPadding(130, 0, 130, 0)

        val color_temp = arrayOf(
            resources.getColor(R.color.color1),
            resources.getColor(R.color.color5),
            resources.getColor(R.color.color3),
            resources.getColor(R.color.color4),
            resources.getColor(R.color.color2),
            resources.getColor(R.color.color1)
        )

        colors = color_temp

        viewPager.setOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position < adapter!!.count - 1 && position < colors!!.size - 1) {
                    viewPager.setBackgroundColor(
                        (argbEvaluator.evaluate(
                            positionOffset,
                            colors!![position],
                            colors!![position + 1]
                        ) as Int)
                    )
                } else {
                    viewPager.setBackgroundColor(colors!![colors!!.size - 1])
                }
            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }


}

