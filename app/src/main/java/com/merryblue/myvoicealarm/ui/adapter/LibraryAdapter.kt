package com.merryblue.myvoicealarm.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.data.model.LibraryItem
import de.coldtea.smplr.smplralarm.models.AlarmItem

class LibraryAdapter(
    val context: Context,
    private var listItem: ArrayList<LibraryItem>,
    val iteractor: Iteractor
) : RecyclerView.Adapter<LibraryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryHolder {
        return LibraryHolder(parent, R.layout.library_item, iteractor)
    }

    override fun onBindViewHolder(holder: LibraryHolder, position: Int) {
        holder.bindData(listItem[position], context)
    }

    override fun getItemCount(): Int = listItem.size

    interface Iteractor {
        fun onClickItem(model: LibraryItem)
    }
}
