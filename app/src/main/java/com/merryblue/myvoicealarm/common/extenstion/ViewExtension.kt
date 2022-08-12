package com.merryblue.myvoicealarm.common.extenstion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat


fun ViewGroup.inflateView(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun View.show(visible: Boolean) {
    if (visible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun View.showVisible(visible: Boolean) {
    if (visible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}

fun View.click(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun View.longClick(onLongClick: (View) -> Unit) {

    setOnLongClickListener { view ->
        onLongClick.invoke(view)
        true
    }
}

class SafeClickListener(
    private var defaultInterval: Int = 0,
    private val onSafeCLick: (View) -> Unit,
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
//        if ((SystemClock.elapsedRealtime() - lastTimeClicked) < defaultInterval) {
//            return
//        }
//        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}

fun View.invisibility() {
    this.visibility = View.INVISIBLE
}

fun View.setBackground(drawable: Int) {
    background = ResourcesCompat.getDrawable(context.resources, drawable, null)
}

fun showToast(context: Context?, msg: String) {
    if (context != null) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}





