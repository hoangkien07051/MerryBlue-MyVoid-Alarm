package com.merryblue.myvoicealarm.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.data.model.SnoozeIntervalItem


class SnoozeIntervalAdapter(
    val context: Context,
    private var listItem: ArrayList<SnoozeIntervalItem>,
    val iteractor: Iteractor
) : RecyclerView.Adapter<SnoozeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnoozeViewHolder {
        return SnoozeViewHolder(parent, R.layout.snooze_interval_item, iteractor)
    }

    override fun onBindViewHolder(holder: SnoozeViewHolder, position: Int) {
        holder.bindData(listItem[position], context)
    }

    override fun getItemCount(): Int = listItem.size

    interface Iteractor {
        fun onClickItem(model: SnoozeIntervalItem, position: Int)
    }
}


