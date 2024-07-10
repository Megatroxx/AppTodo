package com.example.apptodo.data.network.interceptors

import com.example.apptodo.data.repository.LastKnownRevisionRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


/**
 * Interceptor for adding X-Last-Known-Revision header to outgoing requests.
 *
 * @property lastKnownRevisionRepository Repository for accessing the last known revision.
 */

class LastKnownRevisionInterceptor @Inject constructor(
    private val lastKnownRevisionRepository: LastKnownRevisionRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader(
            "X-Last-Known-Revision",
            lastKnownRevisionRepository.lastKnownRevision.toString()
        )
        return chain.proceed(requestBuilder.build())
    }
}