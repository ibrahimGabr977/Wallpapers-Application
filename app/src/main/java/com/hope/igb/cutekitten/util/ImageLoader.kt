package com.hope.igb.cutekitten.util


import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator


class ImageLoader(private val viewDims: Int) {




        fun loadImage(imageRes: Int): RequestCreator? {
            return Picasso.get()
                    .load(imageRes)
                    .noPlaceholder()
                    .resize(viewDims, viewDims)
        }







    }


