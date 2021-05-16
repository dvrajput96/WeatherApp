package com.app.weatherapp.util

import android.os.SystemClock
import android.view.View
import java.util.*

/**
 * A Debounced OnClickListener
 * Rejects clicks that are too close together in time.
 * This class is safe to use as an OnClickListener for multiple views, and will debounce each one separately.
 */
abstract class DebouncedOnClickListener(private val mMinimumInterval: Long) : View.OnClickListener {
    private val mLastClickMap: MutableMap<View, Long>

    /**
     * Implement this in your subclass instead of onClick
     *
     * @param v The view that was clicked
     */
    abstract fun onDebouncedClick(v: View?)
    override fun onClick(clickedView: View) {
        val previousClickTimestamp = mLastClickMap[clickedView]
        val currentTimestamp = SystemClock.uptimeMillis()
        mLastClickMap[clickedView] = currentTimestamp
        if (previousClickTimestamp == null || Math.abs(currentTimestamp - previousClickTimestamp.toLong()) > mMinimumInterval) {
            onDebouncedClick(clickedView)
        }
    }

    /**
     * The one and only constructor
     *
     * @param minimumIntervalMsec The minimum allowed time between clicks - any click sooner than this after a previous click will be rejected
     */
    init {
        mLastClickMap = WeakHashMap()
    }
}