package com.wireless.stickie

import android.animation.ArgbEvaluator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.wireless.stickie.Adapter.CardAdapter
import com.wireless.stickie.Model.ModelCard
import kotlin.collections.ArrayList

class CongestionLearning : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    var adapter: CardAdapter? = null
    lateinit var modelCards: ArrayList<ModelCard>
    var colors: Array<Int>? = null
    var argbEvaluator = ArgbEvaluator()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_view)
        modelCards = ArrayList()
        modelCards.add(ModelCard(R.drawable.solution, getString(R.string.model_layer_topic), ""))
        modelCards.add(
            ModelCard(
                R.drawable.empty,
                getString(R.string.congestion),
                getString(R.string.congestionDesc)
            )
        )
        modelCards.add(
            ModelCard(
                R.drawable.empty,
                getString(R.string.tahoe),
                getString(R.string.tahoeDesc)
            )
        )
        modelCards.add(
            ModelCard(
                R.drawable.empty,
                getString(R.string.reno),
                getString(R.string.renoDesc)
            )
        )
        modelCards.add(
            ModelCard(
                R.drawable.tahoereno,
                "",
                ""
            )
        )


        adapter = CardAdapter(modelCards, this)

        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = adapter
        viewPager.setPadding(130, 0, 130, 0)

        val color_temp = arrayOf(
            resources.getColor(R.color.color1),
            resources.getColor(R.color.color2),
            resources.getColor(R.color.color3),
            resources.getColor(R.color.color4),
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
                        ) as Int)!!
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