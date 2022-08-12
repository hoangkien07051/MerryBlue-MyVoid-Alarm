package com.merryblue.myvoicealarm.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment : DialogFragment() {

    @SuppressLint("ResourceType")
    @IdRes
    abstract fun layoutIdRes(): Int

    abstract fun setUpView()

    abstract fun fireData()

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutIdRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSetupDialog()
        setUpView()
        fireData()
    }

    private fun onSetupDialog() {
        try {
            if (dialog == null) return
            val window = dialog!!.window ?: return
            window.setBackgroundDrawableResource(android.R.color.transparent)
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog?.setCancelable(false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}