package com.wireless.stickie.Adapter

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.wireless.stickie.Common.Common
import com.wireless.stickie.Interface.IOnRecyclerViewItemClickListener
import com.wireless.stickie.Model.CurrentQuestion
import com.wireless.stickie.R
import kotlinx.android.synthetic.main.layout_question_list_helper_item.view.*

class QuestionListHelperAdapter(
    internal var context: Context,
    var answerSheetList: List<CurrentQuestion>
) : RecyclerView.Adapter<QuestionListHelperAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(p0: View?) {
            iOnRecyclerViewItemClickListener.onClick(p0!!,adapterPosition)
        }

        internal var txt_question_num:TextView
        internal var layout_wrapper:LinearLayout

        lateinit var iOnRecyclerViewItemClickListener: IOnRecyclerViewItemClickListener

        fun setiOnRecyclerViewItemClickListener(iOnRecyclerViewItemClickListener: IOnRecyclerViewItemClickListener) {
            this.iOnRecyclerViewItemClickListener = iOnRecyclerViewItemClickListener
        }

        init {
            txt_question_num = itemView.findViewById(R.id.txt_question_num) as TextView
            layout_wrapper = itemView.findViewById(R.id.layout_wrapper) as LinearLayout

            itemView.setOnClickListener(this)
        }

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_question_list_helper_item,p0,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return answerSheetList.size
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, p1: Int) {
        myViewHolder.txt_question_num.text = (p1+1).toString()
        if (answerSheetList[p1].type == Common.ANSWER_TYPE.RIGHT_ANSWER) {
            myViewHolder.layout_wrapper.setBackgroundResource(R.drawable.grid_item_right_answer)
        } else if (answerSheetList[p1].type == Common.ANSWER_TYPE.WRONG_ANSWER) {
            myViewHolder.layout_wrapper.setBackgroundResource(R.drawable.grid_item_wrong_answer)
        } else {
            myViewHolder.layout_wrapper.setBackgroundResource(R.drawable.grid_item_no_answer)
        }

        // When user Click on this item we will navigate to this question
        myViewHolder.setiOnRecyclerViewItemClickListener(object: IOnRecyclerViewItemClickListener{
            override fun onClick(view: View, position: Int) {
                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(Intent(Common.KEY_GO_TO_QUESTION).putExtra(Common.KEY_GO_TO_QUESTION,position))
            }

        })
    }

}