/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.api.okhttp3.interceptors

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.MultipartBody
import okhttp3.Response
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import okio.GzipSource
import timber.log.Timber
import java.io.EOFException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * Logs network traffic
 *
 * @since 1.0.0
 */
class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        val requestBody = request.body()
        val protocol = chain.connection()?.protocol() ?: ""
        val redactBody = request.header(REDACT_BODY) != null

        var tempLogStatement = "$OUTGOING ${request.method()} ${request.url()} $protocol"
        var paddedStatement = "$OUTGOING ".padEnd(tempLogStatement.length, '=')

        log(paddedStatement)
        log(tempLogStatement)
        log(paddedStatement)

        val headers = request.headers()

        logHeaders(OUTGOING, headers)

        when {
            requestBody == null             -> log("$OUTGOING END ${request.method()}")
            bodyHasUnknownEncoding(headers) -> log("$OUTGOING END ${request.method()} (encoded body omitted)")

            else                            -> {
                if (!redactBody && requestBody is MultipartBody) {
                    log(OUTGOING)
                    log("$OUTGOING request body: ${requestBody.type()}")
                    log(OUTGOING)

                    for (i in 0 until requestBody.size()) {
                        val part = requestBody.part(i)

                        log("$OUTGOING part: ${part.body().contentType()}")
                        log("$OUTGOING ${part.headers()}")

                        val buffer = Buffer()

                        part.body().writeTo(buffer)

                        if (isPlaintext(buffer)) {
                            log("$OUTGOING ${buffer.readUtf8()}")
                        } else {
                            log("$OUTGOING [Binary data]")
                        }

                        if (i < requestBody.size()) {
                            log(OUTGOING)
                        }
                    }
                } else if (!redactBody) {
                    val buffer = Buffer()

                    requestBody.writeTo(buffer)

                    val charset = requestBody.contentType()?.charset() ?: UTF8

                    log(OUTGOING)

                    if (isPlaintext(buffer)) {
                        log("$OUTGOING ${buffer.readString(charset)}")
                        log("$OUTGOING END ${request.method()} (${requestBody.contentLength()}-byte body)")
                    } else {
                        log("$OUTGOING ${request.method()} (binary ${requestBody.contentLength()}-byte body omitted)")
                    }
                } else {
                    log("$OUTGOING ")
                    log("$OUTGOING [body redacted]")
                }
            }
        }

        val startNs = System.nanoTime()

        val response = try {
            chain.proceed(request)
        } catch (exception: Exception) {
            Timber.e(exception, "$INCOMING HTTP FAILED")

            throw exception
        }

        val endNs = TimeUnit.NANOSECONDS.toMillis((System.nanoTime() - startNs))

        tempLogStatement = "$INCOMING ${request.method()} ${response.request().url()} $protocol"
        paddedStatement = "$INCOMING ".padEnd(tempLogStatement.length, '=')

        log(paddedStatement)
        log(tempLogStatement)
        log(paddedStatement)

        logHeaders(INCOMING, response.headers())

        val responseBody = response.body()
        val contentLength = responseBody?.contentLength() ?: -1L
        val responseMessage = if (response.message() == null || response.message().isEmpty()) "" else response.message()
        val bodySize = if (contentLength == -1L) "unknown-length" else "$contentLength-byte"

        when {
            responseBody == null || !HttpHeaders.hasBody(response) -> log("$INCOMING END HTTP")
            bodyHasUnknownEncoding(response.headers())             -> log("$INCOMING END HTTP (encoded body omitted")

            else                                                   -> {
                val source = responseBody.source()
                source.request(Long.MAX_VALUE)

                var buffer = source.buffer()

                if ("gzip".equals(response.headers().get("Content-Encoding"), ignoreCase = true)) {
                    var gzippedResponseBody: GzipSource? = null

                    try {
                        gzippedResponseBody = GzipSource(buffer.clone())
                        buffer = Buffer()
                        buffer.writeAll(gzippedResponseBody)
                    } finally {
                        gzippedResponseBody?.close()
                    }
                }

                var charset = UTF8
                val contentType = responseBody.contentType()

                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

                if (!isPlaintext(buffer)) {
                    log("$INCOMING ")
                    log("$INCOMING END HTTP (binary ${buffer.size()}-byte body omitted)")

                    return response
                }

                if (contentLength != 0L) {
                    log("$INCOMING ")
                    log("$INCOMING ${buffer.clone().readString(charset)}")
                }
            }
        }

        tempLogStatement = "$INCOMING ${response.code()} $responseMessage ${response.request().url()} (took $endNs ms, $bodySize body)"
        paddedStatement = "$INCOMING ".padEnd(tempLogStatement.length, '=')

        log(tempLogStatement)
        log(paddedStatement)

        return response
    }

    private fun log(message: String) {
        Timber.d(message)
    }

    private fun logHeaders(prefix: String, headers: okhttp3.Headers) {
        var i = 0
        val count = headers.size()

        while (i < count) {
            log("$prefix ${headers.name(i)}: ${headers.value(i)}")

            i++
        }
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private fun isPlaintext(buffer: Buffer): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = (if (buffer.size() < 64) buffer.size() else 64).toLong()

            buffer.copyTo(prefix, 0, byteCount)

            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }

                val codePoint = prefix.readUtf8CodePoint()

                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }

            return true
        } catch (e: EOFException) {
            return false // Truncated UTF-8 sequence.
        }
    }

    private fun bodyHasUnknownEncoding(headers: okhttp3.Headers): Boolean {
        val contentEncoding = headers.get("Content-Encoding")

        return (contentEncoding != null
            && !contentEncoding.equals("identity", ignoreCase = true)
            && !contentEncoding.equals("gzip", ignoreCase = true))
    }

    companion object {
        /**
         * Header indicating if a request's body should be logged or not
         *
         * @since 1.0.6
         */
        const val REDACT_BODY = "redact-body"

        private const val OUTGOING = "-->"
        private const val INCOMING = "<--"

        private val UTF8 = Charset.forName("UTF-8")
    }
}