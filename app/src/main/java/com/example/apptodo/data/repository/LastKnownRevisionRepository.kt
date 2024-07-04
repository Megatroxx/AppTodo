package com.example.apptodo.data.repository

class LastKnownRevisionRepository {

    var lastKnownRevision: Int? = 0
        private set

    fun updateRevision(revision: Int) {
        lastKnownRevision = revision
    }
}