package com.mirfatif.mylocation;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import androidx.customview.widget.ViewDragHelper;

public class MySwipeDismissBehavior extends CoordinatorLayout.Behavior<View> {

  private static final float mSensitivity = 0f;
  private static final float mDragDismissThreshold = 1f;

  private ViewDragHelper mViewDragHelper;
  private final OnDismissListener mListener;
  private boolean mInterceptingEvents;

  public interface OnDismissListener {
    void onDismiss();
  }

  public MySwipeDismissBehavior(OnDismissListener listener) {
    mListener = listener;
  }

  public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
    boolean handled = super.onLayoutChild(parent, child, layoutDirection);
    if (ViewCompat.getImportantForAccessibility(child)
        == ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_AUTO) {
      ViewCompat.setImportantForAccessibility(child, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES);
      updateAccessibilityActions(child);
    }
    return handled;
  }

  public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent event) {
    boolean dispatchEventToHelper = mInterceptingEvents;

    switch (event.getActionMasked()) {
      case MotionEvent.ACTION_DOWN -> {
        mInterceptingEvents =
            parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY());
        dispatchEventToHelper = mInterceptingEvents;
      }
      case MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> mInterceptingEvents = false;
    }

    if (dispatchEventToHelper) {
      ensureViewDragHelper(parent);
      return mViewDragHelper.shouldInterceptTouchEvent(event);
    }
    return false;
  }

  public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent event) {
    if (mViewDragHelper != null) {
      mViewDragHelper.processTouchEvent(event);
      return true;
    }
    return false;
  }

  private final ViewDragHelper.Callback dragCallback =
      new ViewDragHelper.Callback() {
        private static final int INVALID_POINTER_ID = -1;

        private int originalCapturedViewLeft;
        private int activePointerId = INVALID_POINTER_ID;

        public boolean tryCaptureView(View child, int pointerId) {

          return (activePointerId == INVALID_POINTER_ID || activePointerId == pointerId);
        }

        public void onViewCaptured(View capturedChild, int activePointerId) {
          this.activePointerId = activePointerId;
          originalCapturedViewLeft = capturedChild.getLeft();

          final ViewParent parent = capturedChild.getParent();
          if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
          }
        }

        public void onViewDragStateChanged(int state) {}

        public void onViewReleased(View child, float xvel, float yvel) {

          activePointerId = INVALID_POINTER_ID;

          final int childWidth = child.getWidth();
          int targetLeft;
          boolean dismiss = false;

          if (shouldDismiss(child, xvel)) {
            targetLeft =
                child.getLeft() < originalCapturedViewLeft
                    ? originalCapturedViewLeft - childWidth
                    : originalCapturedViewLeft + childWidth;
            dismiss = true;
          } else {

            targetLeft = originalCapturedViewLeft;
          }

          if (mViewDragHelper.settleCapturedViewAt(targetLeft, child.getTop())) {
            ViewCompat.postOnAnimation(child, new SettleRunnable(child, dismiss));
          } else if (dismiss && mListener != null) {
            mListener.onDismiss();
          }
        }

        private boolean shouldDismiss(View child, float xvel) {
          if (xvel != 0f) {
            return true;
          } else {
            final int distance = child.getLeft() - originalCapturedViewLeft;
            final int thresholdDistance = Math.round(child.getWidth() * mDragDismissThreshold);
            return Math.abs(distance) >= thresholdDistance;
          }
        }

        public int getViewHorizontalDragRange(View child) {
          return child.getWidth();
        }

        public int clampViewPositionHorizontal(View child, int left, int dx) {
          int min = originalCapturedViewLeft - child.getWidth();
          int max = originalCapturedViewLeft + child.getWidth();
          return Math.min(Math.max(min, left), max);
        }

        public int clampViewPositionVertical(View child, int top, int dy) {
          return child.getTop();
        }
      };

  private void ensureViewDragHelper(ViewGroup parent) {
    if (mViewDragHelper == null) {
      mViewDragHelper = ViewDragHelper.create(parent, mSensitivity, dragCallback);
    }
  }

  private class SettleRunnable implements Runnable {
    private final View view;
    private final boolean dismiss;

    SettleRunnable(View view, boolean dismiss) {
      this.view = view;
      this.dismiss = dismiss;
    }

    public void run() {
      if (mViewDragHelper != null && mViewDragHelper.continueSettling(true)) {
        ViewCompat.postOnAnimation(view, this);
      } else {
        if (dismiss && mListener != null) {
          mListener.onDismiss();
        }
      }
    }
  }

  private void updateAccessibilityActions(View child) {
    ViewCompat.removeAccessibilityAction(child, AccessibilityNodeInfoCompat.ACTION_DISMISS);
    ViewCompat.replaceAccessibilityAction(
        child,
        AccessibilityActionCompat.ACTION_DISMISS,
        null,
        (view, arguments) -> {
          int offset = view.getWidth();
          ViewCompat.offsetLeftAndRight(view, offset);
          view.setAlpha(0f);
          if (mListener != null) {
            mListener.onDismiss();
          }
          return true;
        });
  }
}
