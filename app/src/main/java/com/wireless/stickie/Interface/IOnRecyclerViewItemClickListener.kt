package com.wireless.stickie.Interface

import android.view.View

interface IOnRecyclerViewItemClickListener {
    fun onClick(view: View, position:Int)
}