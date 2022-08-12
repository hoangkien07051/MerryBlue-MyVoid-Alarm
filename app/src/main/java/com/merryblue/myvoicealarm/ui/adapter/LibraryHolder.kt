package com.merryblue.myvoicealarm.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.merryblue.myvoicealarm.common.extenstion.click
import com.merryblue.myvoicealarm.data.model.LibraryItem
import kotlinx.android.synthetic.main.library_item.view.*

class LibraryHolder(parent: ViewGroup, layoutID: Int, private val iteractor: LibraryAdapter.Iteractor) : BaseViewHolder<LibraryItem>(parent, layoutID) {

    override fun bindData(model: LibraryItem, context: Context) {
        itemView.lnDialogItem.click {
            iteractor.onClickItem(model)
            itemView.rdSelectLibrary.isChecked = model.isSelect!!
        }
        itemView.tvLibrary.text =model.title
    }
}
