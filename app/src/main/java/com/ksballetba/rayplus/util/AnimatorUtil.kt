package com.ksballetba.rayplus.util

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator

//系统类
private lateinit var FAST_OUT_SLOW_IN_INTERPOLATOR: LinearOutSlowInInterpolator

//通用系统类
private lateinit var LINER_INTERPOLATOR: AccelerateDecelerateInterpolator

//显示方法一
fun scaleShow(view: View, viewPropertyAnimatorListener: ViewPropertyAnimatorListener) {
    view.visibility = View.VISIBLE
    ViewCompat.animate(view)
        .scaleX(1.0f)
        .scaleY(1.0f)
        .alpha(1.0f)
        .setDuration(800)
        .setListener(viewPropertyAnimatorListener)
        .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
        .start()
}

//隐藏方法一
fun scaleHide(view: View, viewPropertyAnimatorListener: ViewPropertyAnimatorListener) {
    view.visibility = View.VISIBLE
    ViewCompat.animate(view)
        .scaleX(0.0f)
        .scaleY(0.0f)
        .alpha(0.0f)
        .setDuration(800)
        .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
        .setListener(viewPropertyAnimatorListener)
        .start()
}

//显示方法二
fun translateShow(view: View, viewPropertyAnimatorListener: ViewPropertyAnimatorListener){
    view.visibility=View.VISIBLE
    ViewCompat.animate(view)
        .translationY(0.0f)
        .setDuration(400)
        .setListener(viewPropertyAnimatorListener)
        .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
        .start()
}

//隐藏方法二
fun translateHide(view: View,viewPropertyAnimatorListener: ViewPropertyAnimatorListener){
    view.visibility=View.VISIBLE
    ViewCompat.animate(view)
        .translationY(260f)
        .setDuration(400)
        .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
        .setListener(viewPropertyAnimatorListener)
        .start()
}

