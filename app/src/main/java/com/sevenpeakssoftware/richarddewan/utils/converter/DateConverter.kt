package com.sevenpeakssoftware.richarddewan.utils.converter

import androidx.room.TypeConverter
import java.util.*
import kotlin.time.milliseconds

class DateConverter {

    /*
    get the data from stored timestamp
     */
    @TypeConverter
    fun fromTimeStamp(timestamp: Long?) = timestamp?.let { Date(it) }

    /*
    store timestamp to room database table
     */
    @TypeConverter
    fun fromDate(date: Date?) = date?.time
}