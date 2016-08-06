package com.zcj.SlideMenu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.zcj.SlideMenu.Util.ScrollAnimation;


public class SlideMenu extends FrameLayout {

    private View MenuView;
    private View MainView;
    private int menuViewWidth;
    private int downX;
    private Scroller scroller;

    public SlideMenu(Context context) {
        super(context);
        init();
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 当一级子View全部加载完后调用，可以用开初始化子View的引用
     * 这时候无法获取子View宽高
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        MenuView=getChildAt(0);
        MainView=getChildAt(1);
    }

    private void init() {
        scroller = new Scroller(getContext());
    }

//    /**
//     * 测量子View的宽高,如果继承至FrameLayout等有实现该方法的ViewGroup则可以不用重写该方法
//     */
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        //测量MenuView大小
//        menuViewWidth = MenuView.getLayoutParams().width;
//        MenuView.measure(menuViewWidth,heightMeasureSpec);
//        //测量MainView大小
//        MainView.measure(widthMeasureSpec,heightMeasureSpec);
//    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //获取menuView宽高的两种方法
//        //1，使用getMeasuredWidth()获取测量后的宽
//        menuViewWidth=MenuView.getMeasuredWidth();
//        //2，通过layoutParams获取到精确的宽，如果是wrap_content，则获取不到
//        menuViewWidth=MenuView.getLayoutParams().width;
        menuViewWidth=MenuView.getMeasuredWidth();
        MenuView.layout(-menuViewWidth,0,0,b);
        MainView.layout(0,0,r,b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX= (int) (event.getX()-downX);
                int scrollX = getScrollX() - deltaX;
                if(scrollX<-menuViewWidth){
                    scrollX=-menuViewWidth;
                }
                if(scrollX>0){
                    scrollX=0;
                }
                scrollTo(scrollX,0);
                downX= (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                //要实现滚动过程有两种方法
        // 1，通过自定义Animation，如scrollAnimation
//                ScrollAnimation scrollAnimation;
//                if (getScrollX()>-menuViewWidth/2){
//                    //关闭菜单
////                    scrollTo(0,0);//没有滚动过程，不采用
//                    scrollAnimation = new ScrollAnimation(this, 0);
//                }else{
//                    //弹出菜单
////                    scrollTo(-menuViewWidth,0);//没有滚动过程，不采用
//                    scrollAnimation=new ScrollAnimation(this,-menuViewWidth);
//                }
//                startAnimation(scrollAnimation);
//                break;
        //2，使用Scroller实现
                //scroller.startScroll()并未完成滚动过程
                //还要调用computeScroll（）完成滚动
                //但是scroller不会主动调用computeScroll（）
                //我们一般又不主动调用computeScroll（）
                //所以要调用invalidate(),让invalidate（）去调用computeScroll（）
                //invalidate()是重新绘制的方法
                if (getScrollX()>-menuViewWidth/2){
                    //关闭菜单
//                    scrollTo(0,0);//没有滚动过程，不采用
                    closeMenu();
                }else{
                    //弹出菜单
//                    scrollTo(-menuViewWidth,0);//没有滚动过程，不采用
                    openMenu();
                }
                break;
        }
        return true;
    }

    /**
     * 关闭菜单
     */
    private void closeMenu(){
        scroller.startScroll(getScrollX(),0,0-getScrollX(),0,400);
        invalidate();
    }

    /**
     * 弹出菜单
     */
    private void openMenu(){
        scroller.startScroll(getScrollX(),0,-menuViewWidth-getScrollX(),0,400);
        invalidate();
    }
    /**
     * Scroller不会主动调用这个方法完成滚动
     * 而invalidate()会调用这个方法
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){    //返回boolean，判断动画是否完成，
                                                //true表示动画还没完成
                                                //false表示动画已经完成
            scrollTo(scroller.getCurrX(),0);
            invalidate();//递归调用，不断绘制滚动动画
        }
    }

    /**
     * 判断是否拦截touchEvent事件
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX= (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) (ev.getX() - downX);
                if (Math.abs(deltaX) >10){
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 切换菜单
     */
    public void switchMenu(){
        if (getScrollX()==0){
            //弹出菜单
            openMenu();
        }else{
            //关闭菜单
            closeMenu();
        }
    }
}
