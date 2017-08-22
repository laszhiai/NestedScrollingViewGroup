package com.li.jacky.nestedscrollingdemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Jacky on 2017/8/22.
 */

public class MiddleViewGroup extends LinearLayout implements NestedScrollingParent, NestedScrollingChild{

    private static final String TAG = "MiddleViewGroup";

    private NestedScrollingChildHelper mChildHelper;
    private NestedScrollingParentHelper mParentHelper;

    public MiddleViewGroup(Context context) {
        this(context, null);
    }

    public MiddleViewGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MiddleViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mChildHelper = new NestedScrollingChildHelper(this);
        mParentHelper = new NestedScrollingParentHelper(this);
        setNestedScrollingEnabled(true);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.i(TAG, "onStartNestedScroll: ");
        //传给outer
        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL | ViewCompat.SCROLL_AXIS_HORIZONTAL);
        return true;
    }

    @Override
    public boolean startNestedScroll(int axes) {
        Log.i(TAG, "startNestedScroll: ");
        return mChildHelper.startNestedScroll(axes);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        Log.i(TAG, "onNestedScrollAccepted: ");
        mParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(View child) {
        Log.i(TAG, "onStopNestedScroll: ");
        mParentHelper.onStopNestedScroll(child);
        stopNestedScroll();
    }

    @Override
    public void stopNestedScroll() {
        Log.i(TAG, "stopNestedScroll: ");
        mChildHelper.stopNestedScroll();
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.i(TAG, "onNestedPreScroll: ");
        //将事件分发到父类
        if (dispatchNestedPreScroll(dx, dy, consumed, null)) {
            dx -= consumed[0];
            dy -= consumed[1];
        }

        if (dx > 0) {
            if (target.getRight() + dx > getWidth()) {
                dx = target.getRight() + dx - getWidth();
                Log.i("jacky!!!!!1", "onNestedPreScroll: " + dx);
                offsetLeftAndRight(dx);
                consumed[0] += dx;
            }
        } else {
            if (target.getLeft() + dx < 0) {
                dx = target.getLeft() + dx;
                Log.i("jacky!!!!!1", "onNestedPreScroll: " + dx);
                offsetLeftAndRight(dx);
                consumed[0] += dx;
            }
        }

        if (dy > 0) {
            if (target.getBottom() + dy > getHeight()) {
                dy = target.getBottom() +dy- getHeight();
                offsetTopAndBottom(dy);
                consumed[1] += dy;
            }
        } else {
            if (target.getTop() + dy < 0) {
                dy = target.getTop() + dy;
                offsetTopAndBottom(dy);
                consumed[1] += dy;
            }
        }
        Log.i("jacky!!!!!2", "onNestedPreScroll: " + dx);

    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable @Size(value = 2) int[] consumed, @Nullable @Size(value = 2) int[] offsetInWindow) {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public int getNestedScrollAxes() {
        return mParentHelper.getNestedScrollAxes();
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mChildHelper.hasNestedScrollingParent();
    }
}

