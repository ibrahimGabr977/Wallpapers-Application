package com.hope.igb.cutekitten.screens.comman.views


interface ObservableViewMvc<LISTENER_TYPE> {


    fun registerListener(listener: LISTENER_TYPE)

    fun unregisterListener(listener: LISTENER_TYPE)



}