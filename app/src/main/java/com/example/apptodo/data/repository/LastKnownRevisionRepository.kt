package com.example.apptodo.data.repository

import javax.inject.Inject
import javax.inject.Singleton


/**
 * Repository for managing the last known revision number.
 */

@Singleton
class LastKnownRevisionRepository @Inject constructor(){

    var lastKnownRevision: Int? = 0
        private set

    internal fun updateRevision(revision: Int) {
        lastKnownRevision = revision
    }
}