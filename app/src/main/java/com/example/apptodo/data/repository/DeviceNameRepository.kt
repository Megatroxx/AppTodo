package com.example.apptodo.data.repository

import android.content.ContentResolver
import android.provider.Settings

class DeviceNameRepository(private val contentResolver: ContentResolver) {

    fun getDeviceName(): String {
        return Settings.Global.getString(contentResolver, Settings.Global.DEVICE_NAME)
    }

}