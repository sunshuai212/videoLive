package com.aihuishou.commonlib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 类名称：MyScrollView
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/9/1 11:24
 * 描述：TODO
 */
public class MyScrollView  extends ScrollView {

    private GestureDetector mGestureDetector;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化手势
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
    }

    /**
     * touch事件的拦截函数
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //根据手势决定是否拦截子控件的onTouch事件
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }

    /**
     * 控件的手势监听
     */
    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //当纵向滑动的距离大于横向滑动的距离的时候，返回true
            if (Math.abs(distanceY) > Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }
}