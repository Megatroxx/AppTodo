package com.example.apptodo.data.entity

import com.example.apptodo.R

/**
 * Enum representing the relevance levels of TodoItems.
 *
 * @property image Resource ID of the image associated with each relevance level.
 */

enum class Relevance(var image: Int) {
    LOW(R.drawable.low),
    BASIC(R.drawable.low),
    IMPORTANT(R.drawable.hug),
}
