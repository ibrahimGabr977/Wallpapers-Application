package com.hope.igb.cutekitten.screens.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hope.igb.cutekitten.R
import com.hope.igb.cutekitten.adhelper.AdInterstitialHelper
import com.hope.igb.cutekitten.screens.displayimage.DisplayActivity
import com.hope.igb.cutekitten.util.InAppReviewHelper
import com.hope.igb.cutekitten.util.ItemsListController

class MainActivity : AppCompatActivity(), MainRecyclerAdapter.ImageClickedListener{

    private var screenWidth = 0
    private lateinit var adInterstitial: AdInterstitialHelper
    private lateinit var  reviewHelper : InAppReviewHelper



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        screenWidth = intent.getIntExtra("SCREEN_WIDTH", 0)





        adInterstitial = AdInterstitialHelper(this)


        reviewHelper = InAppReviewHelper(this)


    }


    @SuppressLint("NotifyDataSetChanged")
    private fun fetchImages() {
        val recyclerView: RecyclerView = findViewById(R.id.mainRecyclerView)
        recyclerView.setHasFixedSize(true)

        val layoutManager = GridLayoutManager(this, 3)

        recyclerView.layoutManager = layoutManager


        if (noDecorationAddedTo(recyclerView))
            recyclerView.addItemDecoration(MyItemsMarginsDecoration(19, 3, false))


        val adapter = MainRecyclerAdapter(
            this, itemsList(),
            screenWidth, this
        )

        recyclerView.adapter = adapter

        adapter.notifyDataSetChanged()
    }


    private fun noDecorationAddedTo(recyclerView: RecyclerView): Boolean {
        return recyclerView.itemDecorationCount == 0
    }


    override fun onStart() {
        super.onStart()
        fetchImages()


        adInterstitial.initAd(getString(R.string.interstitial_ad_id2))
        showInterstitialAdByChance()


    }





    override fun onImageClicked(position: Int) {

        toDisplayActivity(position)


    }


    private fun itemsList(): Array<Int> {
        return ItemsListController.itemList
    }

    private fun toDisplayActivity(position: Int) {

        val displayIntent = Intent(this@MainActivity, DisplayActivity::class.java)
        displayIntent.putExtra("INITIAL_POSITION", position)
        startActivity(displayIntent)
    }




    private fun showInterstitialAdByChance(){
        val random = (Math.random() * 100).toInt()

        if (random != 0 && random % 3==0)
            adInterstitial.showAd()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        reviewHelper.reviewApp()

    }

}