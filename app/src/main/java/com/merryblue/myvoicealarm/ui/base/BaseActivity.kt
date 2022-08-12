package com.merryblue.myvoicealarm.ui.base

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment

abstract class BaseActivity : AppCompatActivity() {

    private var mLoaderDialog: LoaderDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setupLoading()
    }

    private fun setupLoading() {
        mLoaderDialog = LoaderDialog()
    }

    fun showLoading(isShow: Boolean) {
        if (mLoaderDialog == null) {
            setupLoading()
        }
        try {
            if (isShow) {
                mLoaderDialog?.show(supportFragmentManager, null)
            } else {
                mLoaderDialog?.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * replace fragment
     * [isAddToBackStack] = true -> back old fragment -> recreate fragment
     * [isAddToBackStack] = false -> can't back old fragment
     */
    fun replaceFragment(containerIdRes: Int, fragment: Fragment, isAddToBackStack: Boolean = false) {
        if (isAddToBackStack) {
            supportFragmentManager
                .beginTransaction()
                .replace(containerIdRes, fragment)
                .addToBackStack(fragment.javaClass.name)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(containerIdRes, fragment)
                .commit()
        }
    }

    /***
     * add Fragment
     * [isAddToBackStack] = true -> back old fragment -> onResume fragment
     * [isAddToBackStack] = false -> back to index-2 previous fragment
     */
    fun addFragment(containerIdRes: Int, fragment: Fragment, isAddToBackStack: Boolean = false) {
        try {
            if (isAddToBackStack) {
                supportFragmentManager
                    .beginTransaction()
                    .add(containerIdRes, fragment)
                    .addToBackStack(fragment.javaClass.name)
                    .commit()
            } else {
                supportFragmentManager
                    .beginTransaction()
                    .add(containerIdRes, fragment)
                    .commit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("openScreen error: ${fragment.javaClass::getName}")
        }
    }

    //check android 6.0
    fun hasPermissions(context: Context?, permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    fun requestPermission(permissions: Array<String>, requestCode: Int) {
        return ActivityCompat.requestPermissions(this, permissions, requestCode)
    }
}