package com.example.easy_tiffin.Time_stamp

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object TimestampFormatter {

    fun getFormattedTimestamp(): String {
        val timestamp = Timestamp.now().toDate()
        val dateFormat = SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.getDefault())
        return dateFormat.format(timestamp)
    }
}