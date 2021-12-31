package com.mujeeb.weatherapp.common

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {


    fun toTimeStampFromDate(timeStamp: Long): String {
        val dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)
        return dateFormat.format(Date(timeStamp * 1000))
    }
}