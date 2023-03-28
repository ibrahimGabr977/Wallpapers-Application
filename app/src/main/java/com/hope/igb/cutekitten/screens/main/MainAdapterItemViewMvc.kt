package com.hope.igb.cutekitten.screens.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hope.igb.cutekitten.R
import com.hope.igb.cutekitten.screens.comman.views.BaseObservableViewMvc
import com.hope.igb.cutekitten.util.ImageLoader
import com.squareup.picasso.Callback
import java.lang.Exception

class MainAdapterItemViewMvc (inflater: LayoutInflater, parent: ViewGroup, screen_width : Int)
    : BaseObservableViewMvc<MainAdapterItemViewMvc.MainAdapterItemListener>(), Callback {



    interface MainAdapterItemListener {
        fun onImageClicked(position: Int)
    }


    private var  imageView:ImageView
    private var errorView:TextView
    private var imageLoader: ImageLoader


    init {
        setRootView(inflater.inflate(R.layout.image_holder, parent, false))

        val params = getRootView().layoutParams as ViewGroup.LayoutParams


        val viewDims = ((screen_width * 0.95 / 3.0) - 19.0).toInt()

        params.height = viewDims



        getRootView().layoutParams = params

        imageView = findViewById(R.id.image_holder_image)

        errorView = findViewById(R.id.error_view)

        imageLoader = ImageLoader((viewDims * 1.25).toInt())



    }



     fun bindAdapterItem(imageRes: Int, position: Int){


         imageLoader.loadImage(imageRes)?.into(imageView,this)


         getRootView().setOnClickListener {

             for (listener in getListeners())
                 listener.onImageClicked(position)
         }

     }

    override fun onSuccess() {
        getRootView().visibility = View.VISIBLE
        imageView.visibility = View.VISIBLE
        errorView.visibility = View.GONE
    }

    override fun onError(e: Exception?) {
        getRootView().visibility = View.VISIBLE
        errorView.visibility = View.VISIBLE
        imageView.visibility = View.GONE
    }


}