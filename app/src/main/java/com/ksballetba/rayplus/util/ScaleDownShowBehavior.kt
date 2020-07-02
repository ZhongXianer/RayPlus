package com.ksballetba.rayplus.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.sql.StatementEventListener

class ScaleDownShowBehavior(context: Context?, attrs: AttributeSet?) :
    FloatingActionButton.Behavior(context, attrs) {

    private var isAnimateIng: Boolean = false
    private var isShow: Boolean = true

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
        if ((dyConsumed > 0 || dyUnconsumed > 0) && !isAnimateIng && isShow) {
            translateHide(child, object : StateListener() {
                override fun onAnimationStart(view: View?) {
                    super.onAnimationStart(view)
                    isShow = false
                }
            })
        } else if ((dyConsumed < 0 || dyUnconsumed < 0) && !isAnimateIng && !isShow) {
            translateShow(child, object : StateListener() {
                override fun onAnimationStart(view: View?) {
                    super.onAnimationStart(view)
                    isShow = true
                }
            })
        }
    }

    open inner class StateListener : ViewPropertyAnimatorListener {
        override fun onAnimationStart(view: View?) {
            isAnimateIng = true
        }

        override fun onAnimationEnd(view: View?) {
            isAnimateIng = false
        }

        override fun onAnimationCancel(view: View?) {
            isAnimateIng = false
        }
    }
}