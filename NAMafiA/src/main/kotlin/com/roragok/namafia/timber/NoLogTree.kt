/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.timber

import timber.log.Timber.Tree

class NoLogTree : Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) = Unit
}