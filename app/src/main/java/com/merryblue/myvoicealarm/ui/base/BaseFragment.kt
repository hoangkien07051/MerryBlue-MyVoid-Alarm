package com.merryblue.myvoicealarm.ui.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.sdk.AppLovinSdk
import com.merryblue.myvoicealarm.MainActivity
import java.util.concurrent.TimeUnit
import kotlin.math.pow

abstract class BaseFragment : Fragment() {
    // AppLovin Ads integrate
    private var interstitialAd: MaxInterstitialAd? = null
    private var retryAttempt = 0.0
    var interstitialAdUnitId = "dsfsds-fsffff"

    /**
     * init view
     */
    abstract fun setUpView()

    abstract fun observable()

    /**
     * Load data default
     */
    abstract fun fireData()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        observable()
        fireData()
    }

    fun showLoading(isShow: Boolean) {
        try {
            (requireActivity() as BaseActivity).showLoading(isShow)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun replaceFragment(containerIdRes: Int, fragment: Fragment, isAddToBackStack: Boolean = false) {
        (activity as BaseActivity).replaceFragment(containerIdRes, fragment, isAddToBackStack)
    }

    fun addFragment(containerIdRes: Int, fragment: Fragment, isAddToBackStack: Boolean = false) {
        (activity as BaseActivity).addFragment(containerIdRes, fragment, isAddToBackStack)
    }

    fun onBackPress() {
        requireActivity().onBackPressed()
    }

    override fun onDestroyView() {
        interstitialAd?.destroy()
        super.onDestroyView()
    }

    //AppLovin Ads Integrate!!!
    fun initInterstitialAd() {
//        interstitialAd = MaxInterstitialAd(interstitialAdUnitId, requireActivity())
//        interstitialAd?.setListener(object : MaxAdListener {
//            override fun onAdLoaded(ad: MaxAd) {
//                Log.d(TAG, "InterstitialAd: onAdLoaded")
//                retryAttempt = 0.0
//            }
//
//            override fun onAdDisplayed(ad: MaxAd) {
//                Log.d(TAG, "InterstitialAd: onAdDisplayed")
//            }
//
//            override fun onAdHidden(ad: MaxAd) {
//                Log.d(TAG, "InterstitialAd: onAdHidden")
//                interstitialAd?.loadAd()
//                onBackPress()
//            }
//
//            override fun onAdClicked(ad: MaxAd) {
//                Log.d(TAG, "InterstitialAd: onAdClicked")
//            }
//
//            override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
//                Log.d(TAG, "InterstitialAd: onAdLoadFailed")
//                retryAttempt++
//                val delayMillis =
//                    TimeUnit.SECONDS.toMillis(2.0.pow(6.0.coerceAtMost(retryAttempt)).toLong())
//                Handler(Looper.getMainLooper()).postDelayed({ interstitialAd?.loadAd() }, delayMillis)
//            }
//
//            override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
//                Log.d(TAG, "InterstitialAd: onAdDisplayFailed")
//                interstitialAd?.loadAd()
//            }
//        })
//        interstitialAd?.setRevenueListener { Log.d(TAG, "InterstitialAd: onAdRevenuePaid") }
//        interstitialAd?.loadAd()
    }

    fun showInterstitialAd() {
//        AppLovinSdk.getInstance(context).showMediationDebugger()
//        if (interstitialAd?.isReady == true) {
//            interstitialAd?.showAd()
//        } else {
//            Log.d(TAG, "InterstitialAd is not ready!!")
//            interstitialAd?.loadAd()
//        }
    }

    companion object {
        private const val TAG = "BaseFragment"
    }

}