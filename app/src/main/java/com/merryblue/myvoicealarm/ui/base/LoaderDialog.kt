package com.merryblue.myvoicealarm.ui.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.databinding.DialogLoaderBinding

class LoaderDialog : AppCompatDialogFragment() {

    private lateinit var dataBinding: DialogLoaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NO_FRAME, R.style.ThemeDialogLoader)
        isCancelable = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dataBinding = DialogLoaderBinding.inflate(inflater)
        return dataBinding.root
    }

    override fun onStart() {
        super.onStart()
        dataBinding.progressBar.alpha = 0f
        dataBinding.progressBar.animate()
            .alpha(1f)
            .setStartDelay(200).duration = 200
    }

}