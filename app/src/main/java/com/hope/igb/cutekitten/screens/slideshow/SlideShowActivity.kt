package com.hope.igb.cutekitten.screens.slideshow

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdView
import com.hope.igb.cutekitten.R
import com.hope.igb.cutekitten.adhelper.AdBannerHelper
import com.hope.igb.cutekitten.adhelper.AdmobHelper
import com.hope.igb.cutekitten.util.FullScreenInflater
import com.hope.igb.cutekitten.util.ItemsListController

class SlideShowActivity : AppCompatActivity(), AdBannerHelper.AdmobBannerListener {

    private lateinit var viewPager: NonSwipedViewPager
    private val itemsList: Array<Int> = ItemsListController.itemList
    private lateinit var mainHandler: Handler
    private lateinit var adViewTop: AdView
    private lateinit var adViewBottom: AdView
    private lateinit var adBannerHelper1: AdBannerHelper
    private lateinit var adBannerHelper2: AdBannerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_slide_show)
        FullScreenInflater.setFullScreen(this)



        initPagerAdapter()
        mainHandler = Handler(Looper.getMainLooper())



        initAds()


    }


    private fun initAds(){
        adViewTop = findViewById(R.id.slideShowAdViewTop)
        adViewBottom = findViewById(R.id.slideShowAdViewBottom)


        adBannerHelper1 = AdBannerHelper(AdmobHelper.adBannerTop, adViewTop)
        adBannerHelper2 = AdBannerHelper(AdmobHelper.adBannerBottom, adViewBottom)

    }







    override fun onStart() {
        super.onStart()
        startSliding()

        adBannerHelper1.loadAd()
        adBannerHelper2.loadAd()
        adBannerHelper1.registerListener(this)
        adBannerHelper2.registerListener(this)
    }


    override fun onStop() {
        super.onStop()

        adBannerHelper1.unregisterListener(this)
        adBannerHelper2.unregisterListener(this)
    }



    private fun initPagerAdapter() {
        viewPager = findViewById(R.id.slideShowViewPager)
        val adapter = SlideShowAdapter(itemsList)
        viewPager.adapter = adapter
        viewPager.setSwipedEnabled(false)
    }


    private fun startSliding() {
        val runnable: Runnable = object : Runnable {
            var i: Int = 0
            override fun run() {


                viewPager.setCurrentItem(i, true)
                i++
                mainHandler.postDelayed(this, 950L)

                if (i >= itemsList.size)
                    i = 0
            }
        }

        mainHandler.postDelayed(runnable, 950L)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        mainHandler.removeCallbacksAndMessages(null)
        finish()
    }

    override fun onAdLoadedSuccess(adName: String) {

        if (adName == AdmobHelper.adBannerTop)
            adViewTop.visibility = View.VISIBLE

        else if (adName == AdmobHelper.adBannerBottom)
            adViewBottom.visibility = View.VISIBLE
    }




    override fun onAdLoadedFailed(adName: String) {

        if (adName == AdmobHelper.adBannerTop)
            adViewTop.visibility = View.GONE

        else if (adName == AdmobHelper.adBannerBottom)
            adViewBottom.visibility = View.GONE
    }

}