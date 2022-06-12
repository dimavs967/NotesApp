package com.task.notes.utils

import java.util.Calendar

object DateHelper {

    private val cal = Calendar.getInstance()

    fun getCurrentDateAndTime(): String {
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        return "$hour:$minute\n$day/$month/$year"
    }

}