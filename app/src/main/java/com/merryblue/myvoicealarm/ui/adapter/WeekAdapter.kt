package com.merryblue.myvoicealarm.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.data.model.WeekItem

class WeekAdapter(
    val context: Context,
    private var listItem: ArrayList<WeekItem>,
    val iteractor: Iteractor
) : RecyclerView.Adapter<WeekHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekHolder {
        return WeekHolder(parent, R.layout.layout_week_item, iteractor)
    }

    override fun onBindViewHolder(holder: WeekHolder, position: Int) {
        holder.bindData(listItem[position], context)
    }

    override fun getItemCount(): Int = listItem.size

    interface Iteractor {
        fun onClickItem(model: WeekItem, position: Int)
    }
}
