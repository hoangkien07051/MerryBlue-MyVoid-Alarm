package com.merryblue.myvoicealarm.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.extenstion.click
import kotlinx.android.synthetic.main.dialog_delete_fragment.*


class DeleteDialogFragment(
    private val content: String,
    private val callback: (isConfirm: Boolean) -> Unit,
) : BaseDialogFragment() {

    @SuppressLint("ResourceType")
    override fun layoutIdRes(): Int {
        return R.layout.dialog_delete_fragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setUpView() {
        tvCancel.click {
            callback(false)
            dismiss()
        }

        tvConfirmDelete.click {
            callback(true)
            dismiss()
        }

    }

    override fun fireData() {

    }


}
