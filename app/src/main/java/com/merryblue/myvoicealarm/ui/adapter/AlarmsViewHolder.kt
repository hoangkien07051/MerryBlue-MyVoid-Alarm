package com.merryblue.myvoicealarm.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.merryblue.myvoicealarm.common.extenstion.click
import com.merryblue.myvoicealarm.common.extenstion.longClick
import de.coldtea.smplr.smplralarm.models.AlarmItem
import kotlinx.android.synthetic.main.item_alarm.view.*

class AlarmsViewHolder(parent: ViewGroup, layoutID: Int, private val iteractor: AlarmsAdapter.Iteractor) : BaseViewHolder<AlarmItem>(parent, layoutID) {
    override fun bindData(model: AlarmItem, context: Context) {
        itemView.switchAlarm.click {
            model.isActive = itemView.switchAlarm.isChecked
            iteractor.onUpdateAlarm(model, layoutPosition)
        }
        itemView.llItem.click {
            iteractor.onClickItem(model, layoutPosition)
        }
        itemView.tvName.text = model.nameAlarm
        itemView.tvTime.text = model.getTime()
        itemView.tvDays.text = model.getTimes()

        itemView.llItem.longClick {
            iteractor.onRemoveAlarm(model, layoutPosition)
        }
    }
}
