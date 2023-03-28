package com.hope.igb.cutekitten.screens.displayimage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class DisplayPagerAdapter(private val images: Array<Int>)
    :PagerAdapter(){



    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val viewMvc = DisplayItemViewMvc(LayoutInflater.from(container.context), container)
        container.addView(viewMvc.getRootView())

        viewMvc.bindViewAdapter(images[position])


        return viewMvc.getRootView()
    }



    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

}