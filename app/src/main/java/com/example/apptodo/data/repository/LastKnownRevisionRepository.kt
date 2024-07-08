package com.example.apptodo.data.repository


/**
 * Repository for managing the last known revision number.
 */

class LastKnownRevisionRepository {

    var lastKnownRevision: Int? = 0
        private set

    internal fun updateRevision(revision: Int) {
        lastKnownRevision = revision
    }
}