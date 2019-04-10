/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.events.messages

import android.content.Context

class StringContent private constructor(
    private val resId: Int? = null,
    private val args: Array<Any?>? = null,
    private val string: String? = null
) {

    constructor(resId: Int) : this(resId, null, null)
    constructor(resId: Int, args: Array<Any?>) : this(resId, args, null)
    constructor(message: String) : this(null, null, message)

    fun resolve(context: Context): String {
        return when {
            resId != null && args != null -> context.getString(resId, args)
            resId != null                 -> context.getString(resId)
            string != null                -> string
            else                          -> throw RuntimeException("no string construct was provided")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (this === other) {
            return true
        }

        if (other !is StringContent) {
            return false
        }

        if (args != null) {
            if (other.args == null) {
                return false
            }

            if (!args.contentEquals(other.args)) {
                return false
            }
        } else if (other.args != null) {
            return false
        }

        return resId == other.resId && string == other.string
    }

    override fun hashCode(): Int = resId.hashCode() + (args?.contentHashCode() ?: 0) + string.hashCode()
}