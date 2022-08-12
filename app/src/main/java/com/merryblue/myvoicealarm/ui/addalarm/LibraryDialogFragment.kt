package com.merryblue.myvoicealarm.ui.addalarm

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.extenstion.click
import com.merryblue.myvoicealarm.common.extenstion.show
import com.merryblue.myvoicealarm.data.model.LibraryItem
import com.merryblue.myvoicealarm.ui.adapter.LibraryAdapter
import com.merryblue.myvoicealarm.ui.base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_delete_fragment.tvCancel
import kotlinx.android.synthetic.main.library_dialog.*

class LibraryDialogFragment(
    private val listAudio: ArrayList<LibraryItem>,
    private val callback: (item: LibraryItem) -> Unit,
) : BaseDialogFragment() {
    private var libraryAdapter: LibraryAdapter? = null
    private var libraryItem: LibraryItem? = null

    @SuppressLint("ResourceType")
    override fun layoutIdRes(): Int = R.layout.library_dialog

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            if (dialog == null) return
            val window = dialog!!.window ?: return
            window.setBackgroundDrawableResource(R.drawable.bg_white)
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog?.setCancelable(false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun setUpView() {
        initRecyclerView()
        tvCancel.click {
//            callback(libraryItem)
            dismiss()
        }

        tvAdd.click {
            libraryItem?.let{
                callback(it)
            }
            dismiss()
        }
    }

    private fun initRecyclerView(){
        if (listAudio.isEmpty()) rvLibrary.show(false)

        if (libraryAdapter == null) libraryAdapter = LibraryAdapter(requireContext(),listAudio,object : LibraryAdapter.Iteractor{
            override fun onClickItem(model: LibraryItem) {
                model.isSelect = true
                libraryItem = model
                libraryAdapter?.notifyDataSetChanged()
            }

        })
        rvLibrary.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = libraryAdapter
        }
    }

    override fun fireData() = Unit
}