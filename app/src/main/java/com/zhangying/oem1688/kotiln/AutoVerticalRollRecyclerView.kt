package com.zhangying.myapplication.shangxia

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

/**
 * Author：mj
 * Time: 2018/10/9 16:27
 * 描述：
 * Version:
 */
class AutoVerticalRollRecyclerView @JvmOverloads constructor(
		context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr), Runnable {

    /**是否正在滑动*/
    private var mIsRolling = false

    /**
     * View被添加到界面时触发
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startRoll()
    }

    /**
     * View从界面移除时触发
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopRoll()
    }

    /**
     * 开始滑动
     */
    private fun startRoll() {
        //已经滑动时/没有设置适配器时直接返回
        if (mIsRolling && adapter == null) return
        mIsRolling = true
        postDelayed(this, 30)
    }

    /**
     * 停止滑动
     */
    private fun stopRoll() {
        if (!mIsRolling) return
        mIsRolling = false
        removeCallbacks(this)
    }


    override fun run() {
        if (this != null && this.mIsRolling) {
            scrollBy(0, 2)
            postDelayed(this, 30)
        }
    }


    /**
     * 事件触摸：
     * 1.若让滑动不受用户触摸影响，直接返回false，表示不处理事件
     * 2.若需要在用户触摸时停止，用户离开时开始，只需要根据情况触摸事件进行处理即可
     */
    override fun onTouchEvent(e: MotionEvent?): Boolean {
        //1.若让滑动不受用户触摸影响，直接返回false，表示不处理事件
        return false
        //2.若需要在用户触摸时停止，用户离开时开始，只需要根据情况触摸事件进行处理即可
        //        when(e?.action){
        //            MotionEvent.ACTION_DOWN,MotionEvent.ACTION_MOVE -> stopRoll()
        //            MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL,MotionEvent.ACTION_OUTSIDE -> startRoll()
        //        }
        //        return super.onTouchEvent(e)
    }
}
