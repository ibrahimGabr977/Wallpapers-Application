package com.hope.igb.cutekitten.util

import android.app.Activity
import android.content.Context
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.google.android.play.core.tasks.Task

class InAppReviewHelper(private val activity: Activity) {


    private val manager: ReviewManager = ReviewManagerFactory.create(activity)
    private val request: Task<ReviewInfo> = manager.requestReviewFlow()


    fun reviewApp() {

        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // We got the ReviewInfo object
                val reviewInfo = task.result

                val flow = manager.launchReviewFlow(activity, reviewInfo)


                flow.addOnCompleteListener {}
            }
        }
    }


}