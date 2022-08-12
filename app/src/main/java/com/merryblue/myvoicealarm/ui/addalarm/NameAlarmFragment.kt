package com.merryblue.myvoicealarm.ui.addalarm

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.extenstion.click
import com.merryblue.myvoicealarm.ui.base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_name_dialog.*
import kotlinx.android.synthetic.main.dialog_repeat.tvAdd
import kotlinx.android.synthetic.main.dialog_repeat.tvCancel

class NameAlarmFragment(
    private val name: String,
    private val callback: (name: String) -> Unit,
): BaseDialogFragment() {

    @SuppressLint("ResourceType")
    override fun layoutIdRes(): Int = R.layout.dialog_name_dialog

    override fun setUpView() {
        tvCancel.click {
            callback("")
            dismiss()
        }

        tvAdd.click {
            if (edtNameAlarm.text.toString().isNotEmpty()) {
                callback(edtNameAlarm.text.toString().trim())
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Please input Alarm Name",Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun fireData() = Unit

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
}