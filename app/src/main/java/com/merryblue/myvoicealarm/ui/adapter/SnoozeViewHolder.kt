package com.merryblue.myvoicealarm.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.merryblue.myvoicealarm.common.extenstion.click
import com.merryblue.myvoicealarm.data.model.SnoozeIntervalItem
import kotlinx.android.synthetic.main.snooze_interval_item.view.*

class SnoozeViewHolder(
    parent: ViewGroup,
    layoutID: Int,
    private val iteractor: SnoozeIntervalAdapter.Iteractor
) : BaseViewHolder<SnoozeIntervalItem>(parent, layoutID) {
    override fun bindData(model: SnoozeIntervalItem, context: Context) {
        itemView.click {
            iteractor.onClickItem(model, layoutPosition)
        }

        itemView.rbSnooze.isChecked = model.isSelected
        itemView.snooze_interval_value.text = model.minutes.toString()

    }
}
