package com.hope.igb.cutekitten.screens.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MainRecyclerAdapter(
    private val context: Context, private val items: Array<Int>,
    private val screenWidth: Int, private val listener: ImageClickedListener
) :
    RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>(),
    MainAdapterItemViewMvc.MainAdapterItemListener {


    interface ImageClickedListener {
        fun onImageClicked(position: Int)
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val itemViewMvc = MainAdapterItemViewMvc(LayoutInflater.from(context), parent, screenWidth)
        return MainViewHolder(itemViewMvc)

    }


    override fun onViewDetachedFromWindow(holder: MainViewHolder) {
        super.onViewDetachedFromWindow(holder)
            holder.itemViewMvc.unregisterListener(this)

    }


    override fun onViewAttachedToWindow(holder: MainViewHolder) {
        super.onViewAttachedToWindow(holder)
            holder.itemViewMvc.registerListener(this)
    }


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

         holder.itemViewMvc.bindAdapterItem(items[position], position)
    }


    override fun getItemCount(): Int {
        return items.size
    }


    override fun onImageClicked(position: Int) {
        listener.onImageClicked(position)
    }


    class MainViewHolder(val itemViewMvc: MainAdapterItemViewMvc) :
        RecyclerView.ViewHolder(itemViewMvc.getRootView())




}