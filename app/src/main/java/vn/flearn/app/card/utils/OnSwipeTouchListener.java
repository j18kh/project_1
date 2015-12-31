/*
 * Copyright (c) 2013 Ohganic, Inc. All Rights Reserved.
 *
 *  OnSwipeTouchListener.java
 *  Created on Jun 7, 2013
 */
package vn.flearn.app.card.utils;

import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * OnSwipeTouchListener
 * 
 */
public class OnSwipeTouchListener implements OnTouchListener {

    @SuppressWarnings("deprecation")
    private final GestureDetector gestureDetector = new GestureDetector(
            new GestureListener());

    /**
     * @Function name: onTouch
     * @Description on toucth
     * @param view
     *            View
     * @param motionEvent
     *            {@link android.view.MotionEvent}
     * @return {@link Boolean}
     */
    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    /**
     * Private class GestureListener
     * */
    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            onTouchDown();
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * android.view.GestureDetector.SimpleOnGestureListener#onSingleTapConfirmed
         * (android.view.MotionEvent)
         */
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            onTap();
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD
                            && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD
                            && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                }
            } catch (Exception exception) {

                Log.d("OnSwipeTouchListener", "Exception: " + exception.getMessage());
            }

            return result;
        }
    }

    /**
     * @Function name: onSwipeRight
     * @Description onSwipeRight
     */
    public void onSwipeRight() {
    }

    /**
     * @Function name: onSwipeLeft
     * @Description onSwipeLeft
     */
    public void onSwipeLeft() {
    }

    /**
     * @Function name: onSwipeTop
     * @Description onSwipeTop
     */
    public void onSwipeTop() {
    }

    /**
     * @Function name: onSwipeBottom
     * @Description onSwipeBottom
     */
    public void onSwipeBottom() {
    }

    /**
     * @Function name: onTap
     * @Description onTap
     */
    public void onTap() {

    }

    /**
     * @Function name: onSwipeTop
     * @Description onSwipeTop
     */
    public void onTouchDown() {
    }
}
