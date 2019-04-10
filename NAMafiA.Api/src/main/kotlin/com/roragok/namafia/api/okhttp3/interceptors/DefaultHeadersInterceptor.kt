/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.api.okhttp3.interceptors

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

/**
 * Interceptor to add default headers to every request
 *
 * @since 1.0.0
 */
class DefaultHeadersInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response? {
        var request = chain.request()

        if (request.header(ACCEPT) == null) {
            Timber.d("adding header %s: %s", ACCEPT, APPLICATION_JSON)

            request = request.newBuilder().addHeader(ACCEPT, APPLICATION_JSON).build()
        } else {
            Timber.d("%s already present", ACCEPT)
        }

        if (request.header(CONTENT_TYPE) == null) {
            Timber.d("adding header %s: %s", CONTENT_TYPE, APPLICATION_JSON)

            request = request.newBuilder().addHeader(CONTENT_TYPE, APPLICATION_JSON).build()
        } else {
            Timber.d("%s already present", CONTENT_TYPE)
        }

        return chain.proceed(request)
    }

    companion object {
        private const val ACCEPT = "Accept"
        private const val APPLICATION_JSON = "application/json"
        private const val CONTENT_TYPE = "ContentType"
    }
}