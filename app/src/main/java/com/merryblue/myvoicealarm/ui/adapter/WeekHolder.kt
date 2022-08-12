package com.merryblue.myvoicealarm.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.merryblue.myvoicealarm.common.extenstion.click
import com.merryblue.myvoicealarm.data.model.WeekItem
import kotlinx.android.synthetic.main.layout_week_item.view.*

class WeekHolder(parent: ViewGroup, layoutID: Int, private val iteractor: WeekAdapter.Iteractor) : BaseViewHolder<WeekItem>(parent, layoutID) {

    override fun bindData(model: WeekItem, context: Context) {
        itemView.lnWeek.click {
            iteractor.onClickItem(model, adapterPosition)
        }
        itemView.tvWeek.text = model.day
        itemView.cb_once.isChecked= model.isSelect
    }
}