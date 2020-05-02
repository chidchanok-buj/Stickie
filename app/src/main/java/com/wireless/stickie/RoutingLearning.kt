package com.wireless.stickie

import android.animation.ArgbEvaluator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.wireless.stickie.Adapter.CardAdapter
import com.wireless.stickie.Model.ModelCard
import kotlinx.android.synthetic.main.item.*
import kotlin.collections.ArrayList

class RoutingLearning : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    var adapter: CardAdapter? = null
    lateinit var modelCards: ArrayList<ModelCard>
    var colors: Array<Int>? = null
    var argbEvaluator = ArgbEvaluator()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_view)
        modelCards = ArrayList()
        modelCards.add(ModelCard(R.drawable.routing_protocol, getString(R.string.routing_protocols_topic), getString(R.string.routingDes)))
        modelCards.add(ModelCard(R.drawable.empty, getString(R.string.ripTopic), getString(R.string.ripDes)))
        modelCards.add(ModelCard(R.drawable.empty, getString(R.string.bgp), getString(R.string.bgpDesc)))
        modelCards.add(ModelCard(R.drawable.pimdm, getString(R.string.pimdm), getString(R.string.pimdmDesc)))

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

