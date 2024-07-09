package com.example.apptodo.data.repository

import android.content.ContentResolver
import android.provider.Settings
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Repository class for retrieving the device name using the [ContentResolver].
 *
 * @property contentResolver The ContentResolver instance to access device information.
 */

@Singleton
class DeviceNameRepository @Inject constructor(private val contentResolver: ContentResolver) {

    internal fun getDeviceName(): String {
        return Settings.Global.getString(contentResolver, Settings.Global.DEVICE_NAME)
    }

}