package com.task.notes.utils

import java.util.Calendar

class DateHelper {

    private val cal = Calendar.getInstance()

    fun getCurrentTime(): String {
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        return "$hour:$minute"
    }

    fun getCurrentDate(): String {
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        return "$day/$month/$year"
    }

}