package com.li.jacky.nestedscrollingdemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Jacky on 2017/8/22.
 */

public class InnerView extends View implements NestedScrollingChild{

    private static final String TAG = "InnerView";
    private NestedScrollingChildHelper mChildHelper;
    private float ox;
    private float oy;
    private int[] consumed = new int[2];
    private int[] offsetInWindow = new int[2];

    public InnerView(Context context) {
        this(context, null);
    }

    public InnerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InnerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mChildHelper.setNestedScrollingEnabled(true);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable @Size(value = 2) int[] consumed, @Nullable @Size(value = 2) int[] offsetInWindow) {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable @Size(value = 2) int[] offsetInWindow) {
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ox = event.getX();
                oy = event.getY();
                //开始滑动
                startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL | ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:

                float clampedX = event.getX();

                float clampedY = event.getY();
                Log.i("clamped!!!!!", "onTouchEvent:      " + clampedX+"     " +clampedY);

                int dx = (int) (clampedX - ox);
                int dy = (int) (clampedY - oy);

                //分发触屏事件给父类处理
                Log.i("jacky!!!", "onTouchEvent: 1 " + consumed[0] + "        " + consumed[1]);
                if (dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)) {
                    Log.i("jacky!!!", "onTouchEvent: 2 " + consumed[0] + "        " + consumed[1]);
                    //减掉父类消耗的距离
                    dx -= consumed[0];
                    dy -= consumed[1];
                }
                offsetLeftAndRight(dx);
                offsetTopAndBottom(dy);
                break;
            case MotionEvent.ACTION_UP:
                stopNestedScroll();
                break;
        }
        return true;
    }
}
