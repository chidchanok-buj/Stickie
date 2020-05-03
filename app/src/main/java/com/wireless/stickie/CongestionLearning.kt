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
        modelCards.add(ModelCard(R.drawable.empty, getString(R.string.congestion_control_topic), ""))
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
    private fun showChangeLang() {
        val listItems = arrayOf("ภาษาไทย","English")
        val mBuilder = AlertDialog.Builder(this@CongestionLearning)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listItems, -1) { dialog, which ->
            if (which == 0) {
                setLocate("th")
                recreate()
            } else if (which == 1) {
                setLocate("en")
                recreate()
            }
            dialog.dismiss()
        }

        val mDialog = mBuilder.create()

        mDialog.show()
    }

    private fun setLocate(Lang: String?) {
        val config = resources.configuration
        val locale = Locale(Lang)

        Locale.setDefault(locale)
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)

        recreate()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_lang -> {
                showChangeLang()
            }
        }
        return true
    }
}