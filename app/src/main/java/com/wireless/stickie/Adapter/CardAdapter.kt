package com.wireless.stickie.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.wireless.stickie.Model.ModelCard
import com.wireless.stickie.R

class CardAdapter(
    private val modelCards: List<ModelCard>,
    private val context: Context
) :
    PagerAdapter() {
    private lateinit var layoutInflater: LayoutInflater
    override fun getCount(): Int {
        return modelCards.size
    }

    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.item, container, false)
        val imageCard: ImageView
        val titleCard: TextView
        val desc: TextView
        imageCard = view.findViewById(R.id.imageCard)
        titleCard = view.findViewById(R.id.titleCard)
        desc = view.findViewById(R.id.desc)
        imageCard.setImageResource(modelCards[position].image)
        titleCard.text = modelCards[position].title
        desc.text = modelCards[position].desc
        container.addView(view, 0)
        return view
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        container.removeView(`object` as View)
    }

}