/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.timber

import com.crashlytics.android.Crashlytics
import timber.log.Timber.DebugTree
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Tree for logging to crashlytics
 *
 * @version 1.0.0
 * @since 1.0.0
 */
class CrashlyticsTree : DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (message.isBlank()) {
            return
        }

        var msg = message

        if (throwable != null) {
            val sWriter = StringWriter()
            val pWriter = PrintWriter(sWriter)

            throwable.printStackTrace(pWriter)

            msg += ": $sWriter"
        }

        Crashlytics.log(msg)
    }
}