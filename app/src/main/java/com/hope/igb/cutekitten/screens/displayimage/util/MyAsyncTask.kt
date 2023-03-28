package com.hope.igb.cutekitten.screens.displayimage.util

import android.app.Activity
import com.hope.igb.cutekitten.screens.comman.views.BaseObservable

abstract class MyAsyncTask<PROGRESS_LISTENER>(private val activity: Activity?): BaseObservable<PROGRESS_LISTENER>() {




    private fun startBackground() {
        Thread {
            doInBackground()
            activity!!.runOnUiThread { onPostExecute() }
        }.start()
    }

    fun execute() {
        startBackground()
    }

    abstract fun doInBackground()
    abstract fun onPostExecute()

}