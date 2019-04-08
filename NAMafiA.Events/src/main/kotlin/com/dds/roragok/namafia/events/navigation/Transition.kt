/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.dds.roragok.namafia.events.navigation

import com.dds.roragok.namafia.events.R

/**
 * Convenience objects for activity navigation animations. See Activity.applyTransition extension.
 *
 * @param enterAnim animation for entering activity
 * @param exitAnim animation for exiting activity
 * @since 1.0.0
 */
enum class Transition(val enterAnim: Int, val exitAnim: Int) {
    /**
     * Transition to apply an animation where it looks like the entering activity pushes the current activity
     * to the left.
     *
     * @since 1.0.0
     */
    SLIDE_LEFT(R.anim.slide_left_in, R.anim.slide_left_out),

    /**
     * Transition to apply an animation where it looks like the entering activity pushes the current activity
     * to the right.
     *
     * @since 1.0.0
     */
    SLIDE_RIGHT(R.anim.slide_right_in, R.anim.slide_right_out),

    /**
     * Transition to apply an animation where the current activity does not move and the entering activity slides up
     * from the bottom.
     *
     * @since 1.0.0
     */
    SLIDE_UP(R.anim.slide_up_enter, R.anim.slide_up_exit),

    /**
     * Transition to apply an animation where the current activity slides down and the entering activity does not move.
     *
     * @since 1.0.0
     */
    SLIDE_DOWN(0, R.anim.slide_down)
}