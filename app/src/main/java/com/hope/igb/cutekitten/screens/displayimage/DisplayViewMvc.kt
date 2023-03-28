package com.hope.igb.cutekitten.screens.displayimage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.AdView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hope.igb.cutekitten.R
import com.hope.igb.cutekitten.screens.comman.menu.MenuViewMvc
import com.hope.igb.cutekitten.screens.comman.views.BaseObservableViewMvc
import com.konifar.fab_transformation.FabTransformation

@SuppressLint("InflateParams")
class DisplayViewMvc(private val inflater: LayoutInflater) :
    BaseObservableViewMvc<DisplayViewMvc.DisplayListener>(),
    MenuViewMvc.MenuListener {



    interface DisplayListener {
        fun onSaveClicked()
        fun onShareClicked()
        fun onSlideShowClicked()
        fun onSetWallpaperClicked()
        fun onSetLockScreenClicked()
        fun onRefreshClicked()
    }

    private val fab: FloatingActionButton
    private var menuViewMvc: MenuViewMvc? = null
    private var isMenuOpened: Boolean = false
    private val viewPager: ViewPager
    var currentPosition: Int = 0
    val adViewBottom: AdView


    init {
        setRootView(inflater.inflate(R.layout.activity_display, null, false))

        fab = findViewById(R.id.display_open_menu_btn)
        viewPager = findViewById(R.id.displayViewPager)


        fab.setOnClickListener { openMenu() }


        adViewBottom = findViewById(R.id.slideShowAdViewBottom)


    }







     @SuppressLint("ClickableViewAccessibility")
     fun fetchImages(initialPosition: Int, itemsList: Array<Int>){
        val adapter = DisplayPagerAdapter(itemsList)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(initialPosition, true)



     }





    private fun openMenu(){


        menuViewMvc = MenuViewMvc(inflater, getRootView() as ViewGroup)

        FabTransformation.with(fab)
            .transformTo(menuViewMvc?.getMenuContainer())
        isMenuOpened = true

        menuViewMvc?.registerListener(this)


    }


     fun closeMenu(){

         if (menuViewMvc != null && isMenuOpened){

             FabTransformation.with(fab)
                 .transformFrom(menuViewMvc?.getMenuContainer())

             isMenuOpened = false

             menuViewMvc?.unregisterListener(this)


             menuViewMvc = null
         }


    }









    override fun onMenuItemClicked(itemId: Int?) {
        closeMenu()

        currentPosition = viewPager.currentItem

        for (listener in getListeners())

          when(itemId){
              R.id.menu_save -> listener.onSaveClicked()
              R.id.menu_share -> listener.onShareClicked()
              R.id.menu_slide_show -> listener.onSlideShowClicked()
              R.id.menu_set_wallpaper -> listener.onSetWallpaperClicked()
              R.id.menu_set_lock_screen -> listener.onSetLockScreenClicked()
              R.id.menu_refresh -> listener.onRefreshClicked()
          }
    }

}