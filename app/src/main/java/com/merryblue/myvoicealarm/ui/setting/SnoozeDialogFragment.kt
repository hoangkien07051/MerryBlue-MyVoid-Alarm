package com.merryblue.myvoicealarm.ui.setting

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.extenstion.click
import com.merryblue.myvoicealarm.data.model.SnoozeIntervalItem
import com.merryblue.myvoicealarm.ui.adapter.SnoozeIntervalAdapter
import com.merryblue.myvoicealarm.ui.base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_snooze_interval.*


class SnoozeDialogFragment(
    val listItem: ArrayList<SnoozeIntervalItem>,
    private val callback: (snooze: ArrayList<SnoozeIntervalItem>) -> Unit
) : BaseDialogFragment() {

    lateinit var snoozeIntervalAdapter: SnoozeIntervalAdapter

    @SuppressLint("ResourceType")
    override fun layoutIdRes(): Int {
        return R.layout.dialog_snooze_interval
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            if (dialog == null) return
            val window = dialog!!.window ?: return
            window.setBackgroundDrawableResource(android.R.color.transparent)
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog?.setCancelable(false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun setUpView() {
        snoozeIntervalAdapter = SnoozeIntervalAdapter(requireContext(), listItem, object : SnoozeIntervalAdapter.Iteractor {
            override fun onClickItem(model: SnoozeIntervalItem, position: Int) {
                listItem.forEach {
                    it.isSelected = false
                }
                if (model.isSelected) {
                    model.isSelected = false
                } else {
                    val snooze = listItem.firstOrNull { n -> n.isSelected }
                    if (snooze != null) {
                        val index = listItem.indexOf(snooze)
                        if (index >= 0) {
                            listItem.get(index).isSelected = false
                            snoozeIntervalAdapter.notifyItemChanged(index)
                        }
                    }
                    model.isSelected = !model.isSelected
                    snoozeIntervalAdapter.notifyItemChanged(position)
                }
            }
        })

        rcl_snooze_interval.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = snoozeIntervalAdapter
        }



        tvCancel.click {
            dismiss()
        }

        tvConfirmOk.click {
            callback.invoke(listItem)
            dismiss()
        }

    }

    override fun fireData() {

    }


}
