package com.wireless.stickie
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_recyclerview_item.view.*
import java.util.*
class DragDropRecyclerAdapter(private val startDragListener: OnStartDragListener) :
    RecyclerView.Adapter<DragDropRecyclerAdapter.ItemViewHolder>(),
    ItemMoveCallbackListener.Listener {
    private var layers = emptyList<String>().toMutableList()
    fun setLayers(newUsers: List<String>) {
        layers.addAll(newUsers)
    }
    override fun getItemCount(): Int {
        return layers.size
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val user = layers[position]
        holder.bind(user)
        holder.itemView.imageView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                this.startDragListener.onStartDrag(holder)
            }
            return@setOnTouchListener true
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_recyclerview_item, parent, false)
        return ItemViewHolder(itemView)
    }
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(text: String) {
            itemView.ah5555.text = text
        }
    }
    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(layers, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(layers, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }
    override fun onRowSelected(itemViewHolder: ItemViewHolder) {
    }
    override fun onRowClear(itemViewHolder: ItemViewHolder) {
    }
}