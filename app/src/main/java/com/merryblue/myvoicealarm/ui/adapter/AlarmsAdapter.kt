package com.merryblue.myvoicealarm.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.merryblue.myvoicealarm.R
import de.coldtea.smplr.smplralarm.models.AlarmItem

class AlarmsAdapter(
    val context: Context,
    private var listItem: ArrayList<AlarmItem>,
    val iteractor: Iteractor
) : RecyclerView.Adapter<AlarmsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmsViewHolder {
        return AlarmsViewHolder(parent, R.layout.item_alarm, iteractor)
    }

    override fun onBindViewHolder(holder: AlarmsViewHolder, position: Int) {
        holder.bindData(listItem[position], context)
        val param = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        var bottom = context.resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin)
        if (position == listItem.size - 1) {
            bottom = context.resources.getDimensionPixelSize(R.dimen.size_medium)
        }
        param.setMargins(0, 0, 0, bottom)
        holder.itemView.layoutParams = param
    }

    override fun getItemCount(): Int = listItem.size

    interface Iteractor {
        fun onClickItem(model: AlarmItem, position: Int)
        fun onUpdateAlarm(model: AlarmItem, position: Int)
        fun onRemoveAlarm(model: AlarmItem, position: Int)
    }
}
