/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.dds.roragok.namafia.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Convenience method to add a [Disposable] to a [CompositeDisposable]
 *
 * @param disposables the [CompositeDisposable] to add this disposable
 * @since 1.0.0
 */
fun Disposable.addTo(disposables: CompositeDisposable) {
    disposables.add(this)
}