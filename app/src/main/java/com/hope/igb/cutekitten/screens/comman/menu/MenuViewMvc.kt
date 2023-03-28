package com.hope.igb.cutekitten.screens.comman.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.hope.igb.cutekitten.R
import com.hope.igb.cutekitten.screens.comman.views.BaseObservableViewMvc

class MenuViewMvc(inflater: LayoutInflater, parent: ViewGroup) :
    BaseObservableViewMvc<MenuViewMvc.MenuListener>(),View.OnClickListener{

     interface MenuListener {
      fun onMenuItemClicked(itemId: Int?)
    }


    private val menuContainer : LinearLayout


    init {
        setRootView(inflater.inflate(R.layout.menu_layout, parent, true))

        menuContainer = findViewById(R.id.menu_container)

        initMenuItems(menuContainer)
    }



    fun getMenuContainer(): LinearLayout{
        return menuContainer
    }









    private fun initMenuItems(container: ViewGroup){

        val saveItem = container.findViewById<ImageView>(R.id.menu_save)
        val shareItem = container.findViewById<ImageView>(R.id.menu_share)
        val slideShowItem = container.findViewById<ImageView>(R.id.menu_slide_show)
        val setWallpaperItem = container.findViewById<ImageView>(R.id.menu_set_wallpaper)
        val setLockScreenItem = container.findViewById<ImageView>(R.id.menu_set_lock_screen)
        val refreshItem = container.findViewById<ImageView>(R.id.menu_refresh)


        saveItem.setOnClickListener(this)
        shareItem.setOnClickListener(this)
        slideShowItem.setOnClickListener(this)
        setWallpaperItem.setOnClickListener(this)
        setLockScreenItem.setOnClickListener(this)
        refreshItem.setOnClickListener(this)

    }


    override fun onClick(v: View?) {

        for (listener in getListeners())
            listener.onMenuItemClicked(v?.id)


    }



}