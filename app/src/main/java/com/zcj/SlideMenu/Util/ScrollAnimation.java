package com.zcj.SlideMenu.Util;


import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 让指定View在一段时间内scroll到指定位置
 */
public class ScrollAnimation extends Animation {
    private View view;
    private int targetScrollX;
    private int startScrollX;
    private int totalValue;

    public ScrollAnimation(View view, int targetScrollX) {
        this.view = view;
        this.targetScrollX = targetScrollX;
        startScrollX = view.getScrollX();
        totalValue = this.targetScrollX - startScrollX;
        int time = Math.abs(totalValue);
        setDuration(time);
    }

    /**
     * 在指定时间内一直执行该方法，直到动画结束
     *
     * @param interpolatedTime 取值 0 —— 1 标识动画执行的百分比
     * @param t
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float currentScrollX = startScrollX + totalValue * interpolatedTime;
        view.scrollTo((int) currentScrollX, 0);
    }
}