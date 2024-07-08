package com.example.apptodo.data.repository

import android.content.ContentResolver
import android.provider.Settings


/**
 * Repository class for retrieving the device name using the [ContentResolver].
 *
 * @property contentResolver The ContentResolver instance to access device information.
 */


class DeviceNameRepository(private val contentResolver: ContentResolver) {

    internal fun getDeviceName(): String {
        return Settings.Global.getString(contentResolver, Settings.Global.DEVICE_NAME)
    }

}