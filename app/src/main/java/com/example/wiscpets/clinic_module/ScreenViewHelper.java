package com.example.wiscpets.clinic_module;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * A Custom ViewPager for  disabling or enabling the swipe action between pages.
 */
public class ScreenViewHelper extends ViewPager {
	private boolean isSwipeEnabledCustomView;

	public ScreenViewHelper(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.isSwipeEnabledCustomView = true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (this.isSwipeEnabledCustomView) {
			return super.onTouchEvent(event);
		}
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (this.isSwipeEnabledCustomView) {
			return super.onInterceptTouchEvent(event);
		}
		return false;
	}

	/**
	 * To enable or disable swipe.
	 * @param isSwipeEnabledCustomView true to enable swipe
	 */
	public void setPagingEnabled(boolean isSwipeEnabledCustomView) {
		this.isSwipeEnabledCustomView = isSwipeEnabledCustomView;
	}
}