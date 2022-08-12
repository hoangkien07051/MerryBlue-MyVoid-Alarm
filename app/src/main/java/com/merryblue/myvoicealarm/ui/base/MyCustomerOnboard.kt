package com.merryblue.myvoicealarm.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.aemerse.onboard.OnboardAdvanced
import com.aemerse.onboard.OnboardFragment.Companion.newInstance
import com.merryblue.myvoicealarm.MainActivity
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.pref.SharedPrefsHelper
import com.merryblue.myvoicealarm.ui.menu.language.LanguageFragment

class MyCustomerOnboard : OnboardAdvanced() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(
            newInstance(
                getString(R.string.onboard_title_1), getString(R.string.onboard_des_1),
                R.drawable.ic_onboard_1,
                R.drawable.ic_bg_onb,
                resources.getColor(R.color.white),
                resources.getColor(R.color.color9E9E9E), 0, 0, R.drawable.ic_bg_onb
            )
        )
        addSlide(
            newInstance(
                getString(R.string.tv_internet_speed), getString(R.string.onboard_des_2),
                R.drawable.ic_onboard_2,
                R.drawable.ic_bg_onb,
                resources.getColor(R.color.white),
                resources.getColor(R.color.color9E9E9E), 0, 0, R.drawable.ic_bg_onb
            )
        )
        addSlide(
            newInstance(
                getString(R.string.tv_change_vpn), getString(R.string.onboard_des_3),
                R.drawable.ic_onboard_3,
                R.drawable.ic_bg_onb,
                resources.getColor(R.color.white),
                resources.getColor(R.color.color9E9E9E), 0, 0, R.drawable.ic_bg_onb
            )
        )

        val language = SharedPrefsHelper.getString(
            baseContext,
            SharedPrefsHelper.MyPREFERENCES,
            SharedPrefsHelper.SETTING_LANGUAGE
        )
        if (language.isNullOrEmpty())
            addFragment(LanguageFragment(true))
    }

//    override fun attachBaseContext(newBase: Context?) {
//        val language = SharedPrefsHelper.getString(
//            newBase!!,
//            SharedPrefsHelper.MyPREFERENCES,
//            SharedPrefsHelper.SETTING_LANGUAGE
//        )
//        super.attachBaseContext(MyContextWrapper.wrap(newBase, language))
//    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        val intent = Intent(this, MainActivity::class.java)
        val args = Bundle()
        args.putBoolean("onboarding", true)
        intent.putExtras(args)
        SharedPrefsHelper.saveBoolean(
            applicationContext,
            SharedPrefsHelper.MyPREFERENCES,
            SharedPrefsHelper.OPEN_APP_FIRST_TIME,
            false
        )
        startActivity(intent)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        val intent = Intent(this, MainActivity::class.java)
        val args = Bundle()
        args.putBoolean("onboarding", true)
        intent.putExtras(args)
        SharedPrefsHelper.saveBoolean(
            applicationContext,
            SharedPrefsHelper.MyPREFERENCES,
            SharedPrefsHelper.OPEN_APP_FIRST_TIME,
            false
        )
        startActivity(intent)
        finish()
    }

}