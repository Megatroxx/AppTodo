package com.example.apptodo

import android.content.res.ColorStateList
import android.graphics.Color

enum class ResourcesConstantsEnum(var stateList: ColorStateList){

    LOW_BASE_STATE_LIST(ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_checked)
        ),
        intArrayOf(
            Color.parseColor("#34C759"),
            Color.parseColor("#575757")
        )
    )),
    URGENT_STATE_LIST(ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_checked)
        ),
        intArrayOf(
            Color.parseColor("#34C759"),
            Color.parseColor("#FF0000")
        )
    ))
}