package com.hope.igb.cutekitten.screens.displayimage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.hope.igb.cutekitten.R
import com.hope.igb.cutekitten.screens.comman.views.BaseViewMvc

class DisplayItemViewMvc(inflater: LayoutInflater, parent:ViewGroup) : BaseViewMvc() {

    private val imageView:ImageView

    init {
        setRootView(inflater.inflate(R.layout.display_adapter, parent, false))

        imageView=findViewById(R.id.display_adapter_image)

    }


     fun bindViewAdapter(imageRes: Int){
        imageView.setImageResource(imageRes)
    }


}