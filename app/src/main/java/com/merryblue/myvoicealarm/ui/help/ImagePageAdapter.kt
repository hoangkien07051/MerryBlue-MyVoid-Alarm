package com.merryblue.myvoicealarm.ui.help

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.viewpager.widget.PagerAdapter
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.databinding.LayoutImageHelpPagerBinding
import java.util.*
import kotlin.collections.ArrayList

class ImagePageAdapter(
    var context: Context,
    private var listImagePaths: ArrayList<Int>,
) : PagerAdapter() {

    override fun getCount(): Int = listImagePaths.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayoutCompat
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemBinding =
            LayoutImageHelpPagerBinding.inflate(
                LayoutInflater.from(container.context),
                container,
                false
            )
        itemBinding.imgShowViewpager.setImageResource(listImagePaths[position])
        Objects.requireNonNull(container).addView(itemBinding.root)
        return itemBinding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayoutCompat)
    }
}