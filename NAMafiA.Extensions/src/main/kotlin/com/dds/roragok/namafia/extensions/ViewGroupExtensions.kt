/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.dds.roragok.namafia.extensions

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.Callback

/**
 * Convenience method to inflate a view
 *
 * @since 1.0.0
 */
fun ViewGroup.inflate(resId: Int, attachToRoot: Boolean = false): View = LayoutInflater.from(context).inflate(resId, this, attachToRoot)

//
// snackbar overloads using string resource id
//

/**
 * Displays a snackbar
 *
 * @param resId string resource id
 * @param duration duration of the snackbar. Defaults to [Snackbar.LENGTH_LONG]
 * @since 1.1.0
 */
fun ViewGroup.showSnackbar(resId: Int, duration: Int = Snackbar.LENGTH_LONG) {
    showSnackbar(this, context.getString(resId), duration, dismissListener = null, actionResId = null, action = null)
}

/**
 * Displays a snackbar
 *
 * @param resId string resource id
 * @param duration duration of the snackbar. Defaults to [Snackbar.LENGTH_LONG]
 * @param dismissListener callback for when the snackbar is dismissed
 * @since 1.1.0
 */
fun ViewGroup.showSnackbar(resId: Int, duration: Int = Snackbar.LENGTH_LONG, dismissListener: () -> Unit) {
    showSnackbar(this, context.getString(resId), duration, dismissListener, actionResId = null, action = null)
}

/**
 * Displays a snackbar
 *
 * @param resId string resource id
 * @param duration duration of the snackbar. Defaults to [Snackbar.LENGTH_LONG]
 * @param actionResId string resource for the action button
 * @param action callback when the action button is clicked
 * @since 1.1.0
 */
fun ViewGroup.showSnackbar(resId: Int, duration: Int = Snackbar.LENGTH_LONG, actionResId: Int, action: () -> Unit) {
    showSnackbar(this, context.getString(resId), duration, dismissListener = null, actionResId = actionResId, action = action)
}

/**
 * Displays a snackbar
 *
 * @param resId string resource id
 * @param duration duration of the snackbar. Defaults to [Snackbar.LENGTH_LONG]
 * @param dismissListener callback for when the snackbar is dismissed
 * @param actionResId string resource for the action button
 * @param action callback when the action button is clicked
 * @since 1.1.0
 */
fun ViewGroup.showSnackbar(resId: Int, duration: Int = Snackbar.LENGTH_LONG, dismissListener: () -> Unit, actionResId: Int, action: () -> Unit) {
    showSnackbar(this, context.getString(resId), duration, dismissListener, actionResId, action)
}

//
// snackbar overloads using strings
//

/**
 * Displays a snackbar
 *
 * @param message the snackbar message
 * @param duration duration of the snackbar. Defaults to [Snackbar.LENGTH_LONG]
 * @since 1.1.0
 */
fun ViewGroup.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    showSnackbar(this, message, duration, dismissListener = null, actionResId = null, action = null)
}

/**
 * Displays a snackbar
 *
 * @param message the snackbar message
 * @param duration duration of the snackbar. Defaults to [Snackbar.LENGTH_LONG]
 * @param dismissListener callback for when the snackbar is dismissed
 * @since 1.1.0
 */
fun ViewGroup.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_LONG, dismissListener: () -> Unit) {
    showSnackbar(this, message, duration, dismissListener, actionResId = null, action = null)
}

/**
 * Displays a snackbar
 *
 * @param message the snackbar message
 * @param duration duration of the snackbar. Defaults to [Snackbar.LENGTH_LONG]
 * @param actionResId string resource for the action button
 * @param action callback when the action button is clicked
 * @since 1.1.0
 */
fun ViewGroup.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_LONG, actionResId: Int, action: () -> Unit) {
    showSnackbar(this, message, duration, dismissListener = null, actionResId = actionResId, action = action)
}

/**
 * Displays a snackbar
 *
 * @param message the snackbar message
 * @param duration duration of the snackbar. Defaults to [Snackbar.LENGTH_LONG]
 * @param dismissListener callback for when the snackbar is dismissed
 * @param actionResId string resource for the action button
 * @param action callback when the action button is clicked
 * @since 1.1.0
 */
fun ViewGroup.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_LONG, dismissListener: () -> Unit, actionResId: Int, action: () -> Unit) {
    showSnackbar(this, message, duration, dismissListener, actionResId, action)
}

private fun showSnackbar(viewGroup: ViewGroup, text: String, duration: Int = Snackbar.LENGTH_LONG, dismissListener: (() -> Unit)? = null, actionResId: Int?, action: (() -> Unit)? = null) {
    val message = getWhiteSpannableString(text)
    val snackbar = Snackbar.make(viewGroup, message, duration)

    if (dismissListener != null) {
        snackbar.addCallback(object : Callback() {
            override fun onDismissed(transientBottomBar: Snackbar, event: Int) {
                dismissListener()
            }
        })
    }

    if (actionResId != null && action != null) {
        snackbar.setAction(getWhiteSpannableString(viewGroup.context.getString(actionResId))) {
            action()
        }
    }

    snackbar.show()
}

private fun getWhiteSpannableString(message: String): SpannableStringBuilder {
    val builder = SpannableStringBuilder().append(message)
    builder.setSpan(ForegroundColorSpan(Color.WHITE), 0, message.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    return builder
}