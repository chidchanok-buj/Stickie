package com.wireless.stickie.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wireless.stickie.Common.Common
import com.wireless.stickie.Model.Category
import com.wireless.stickie.Interface.IOnRecyclerViewItemClickListener
import com.wireless.stickie.Model.Question
import com.wireless.stickie.QuestionActivity
import com.wireless.stickie.R
import kotlinx.android.synthetic.main.layout_category_item.view.*

class CategoryAdapter(
    internal var context: Context,
    internal var categoryList: List<Category>
) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.layout_category_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txt_category_name.text = categoryList[position].name
        holder.setiOnRecyclerViewItemClickListener(object:IOnRecyclerViewItemClickListener{
            override fun onClick(view: View, position: Int) {
                Common.selecedCategory = categoryList[position]

                val intent = Intent(context, QuestionActivity::class.java)
                context.startActivity(intent)
            }

        })
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        internal var txt_category_name: TextView
        internal var card_category: CardView
        internal lateinit var iOnRecyclerViewItemClickListener:IOnRecyclerViewItemClickListener

        fun setiOnRecyclerViewItemClickListener(iOnRecyclerViewItemClickListener: IOnRecyclerViewItemClickListener) {
            this.iOnRecyclerViewItemClickListener = iOnRecyclerViewItemClickListener
        }

        init {
            txt_category_name = itemView.findViewById(R.id.txt_category_name) as TextView
            card_category = itemView.findViewById(R.id.card_category) as CardView

            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            iOnRecyclerViewItemClickListener.onClick(view,adapterPosition)
        }

    }
}