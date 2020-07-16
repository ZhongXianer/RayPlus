package com.ksballetba.rayplus.util

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ScaleDownShowBehavior(context: Context?, attrs: AttributeSet?) :
    FloatingActionButton.Behavior(context, attrs) {

    @Override
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        if (axes == ViewCompat.SCROLL_AXIS_VERTICAL) {
            return true
        }
        return super.onStartNestedScroll(
            coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            type
        )
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type,
            consumed
        )
        if (dyConsumed > 0 && child.visibility == View.VISIBLE) {
            child.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
                override fun onShown(fab: FloatingActionButton?) {
                    super.onShown(fab)
                }

                override fun onHidden(fab: FloatingActionButton?) {
                    super.onHidden(fab)
                    fab?.visibility = View.INVISIBLE
                }
            })
        } else if (dyConsumed <= 0 && child.visibility != View.VISIBLE) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            child.show()
        }
    }

//    private fun animateOut(button: FloatingActionButton) {
////        ViewCompat.animate(button)
////            .translationY((button.height + getMarginBottom(button)).toFloat())
////            .setInterpolator(INTERPOLATOR).withLayer()
////            .setListener(object : ViewPropertyAnimatorListener {
////                override fun onAnimationEnd(view: View?) {
////                    mIsAnimatingOut = false
////                    view?.visibility = View.GONE
////                }
////
////                override fun onAnimationCancel(view: View?) {
////                    mIsAnimatingOut = false
////                }
////
////                override fun onAnimationStart(view: View?) {
////                    mIsAnimatingOut = true
////                }
////            }).start();
////    }
////
////    private fun animateIn(button: FloatingActionButton) {
////        button.visibility = View.VISIBLE
////        ViewCompat.animate(button).translationY(0F)
////            .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
////            .start()
////    }
////
////    private fun getMarginBottom(v: View): Int {
////        var marginBottom = 0
////        val layoutParams = v.layoutParams
////        if (layoutParams is ViewGroup.MarginLayoutParams) {
////            marginBottom = layoutParams.bottomMargin
////        }
////        return marginBottom
////    }
}