package com.example.apptodo.data.network.exception

/**
 * Custom exception class for network-related errors.
 *
 * @param message The error message.
 */

class NetworkException(override val message: String?): RuntimeException()