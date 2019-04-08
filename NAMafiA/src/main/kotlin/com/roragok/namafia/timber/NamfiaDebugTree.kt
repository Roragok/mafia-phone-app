/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.timber

import timber.log.Timber.DebugTree

class NamfiaDebugTree : DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? =
        String.format("%s:%s", super.createStackElementTag(element), element.lineNumber)
}