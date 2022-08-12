package com.merryblue.myvoicealarm.ui.help

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.extenstion.click
import com.merryblue.myvoicealarm.databinding.ActivityHelpBinding
import com.merryblue.myvoicealarm.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_help.*

class HelpActivity : BaseActivity() {
    private lateinit var binding: ActivityHelpBinding
    private lateinit var listImages: ArrayList<Int>
    private lateinit var mViewPagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        setUpView()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpView() {

        listImages = ArrayList()
        listImages.add(R.drawable.cooked_egg)
        listImages.add(R.drawable.egg_icon)
        listImages.add(R.drawable.egg_notification)
        listImages.add(R.drawable.egg_icon_plain)
        listImages.add(R.drawable.alarm_clock_1)

        if (viewpagerImage.currentItem == 1) {

        }

        mViewPagerAdapter = ImagePageAdapter(this@HelpActivity, listImages)
        viewpagerImage.adapter = mViewPagerAdapter

        viewpagerImage?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                titlePageNo.text = (1 + viewpagerImage.currentItem).toString() + "/5"
                when (viewpagerImage.currentItem) {
                    0 -> {
                        titleHelpPager.text = resources.getString(R.string.title_help_pager_1)
                    }
                    1 -> {
                        titleHelpPager.text = resources.getString(R.string.title_help_pager_2)
                    }
                    2 -> {
                        titleHelpPager.text = resources.getString(R.string.title_help_pager_3)
                    }
                    3 -> {
                        titleHelpPager.text = resources.getString(R.string.title_help_pager_4)
                    }
                    4 -> {
                        titleHelpPager.text = resources.getString(R.string.title_help_pager_5)
                    }
                }
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        btn_previous_page.click {
            viewpagerImage.setCurrentItem(viewpagerImage.currentItem - 1, true)
        }

        btn_next_page.click {
            viewpagerImage.setCurrentItem(viewpagerImage.currentItem + 1, true)
        }

        btnBack.click {
            finish()
        }

    }

}