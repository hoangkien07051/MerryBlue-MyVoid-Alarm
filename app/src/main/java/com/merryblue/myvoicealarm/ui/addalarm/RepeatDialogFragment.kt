package com.merryblue.myvoicealarm.ui.addalarm

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.extenstion.click
import com.merryblue.myvoicealarm.common.extenstion.show
import com.merryblue.myvoicealarm.data.model.WeekItem
import com.merryblue.myvoicealarm.ui.adapter.WeekAdapter
import com.merryblue.myvoicealarm.ui.base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_repeat.*

class RepeatDialogFragment (
    private val listWeek: ArrayList<WeekItem>,
    private val callback: (listWeek: ArrayList<WeekItem>) -> Unit,
) : BaseDialogFragment() {
    private var weekAdapter: WeekAdapter? = null

    @SuppressLint("ResourceType")
    override fun layoutIdRes(): Int = R.layout.dialog_repeat

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            if (dialog == null) return
            val window = dialog!!.window ?: return
            window.setBackgroundDrawableResource(R.drawable.bg_white)
            window.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog?.setCancelable(false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun setUpView() {
        initRecyclerView()
        tvCancel.click {
            callback(listWeek)
            dismiss()
        }

        tvAdd.click {
            if (cb_everyday.isChecked) {
                for (i in listWeek) {
                    i.isSelect = true
                }
            }
            callback(listWeek)
            dismiss()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView(){
        if (listWeek.isEmpty()) rvRepeat.show(false)

        if (weekAdapter == null) weekAdapter = WeekAdapter(requireContext(), listWeek,object : WeekAdapter.Iteractor{
            override fun onClickItem(model: WeekItem, position: Int) {
                model.isSelect = !model.isSelect
                weekAdapter?.notifyDataSetChanged()
            }
        })
        rvRepeat.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = weekAdapter
        }
    }

    override fun fireData() = Unit
}