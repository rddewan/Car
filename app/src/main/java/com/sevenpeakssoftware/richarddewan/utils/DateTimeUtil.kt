package com.sevenpeakssoftware.richarddewan.utils

import android.content.Context
import android.text.format.DateFormat
import com.sevenpeakssoftware.richarddewan.di.scope.ActivityScope
import java.text.SimpleDateFormat
import java.util.*


@ActivityScope
class DateTimeUtil (private val context: Context) {

    fun getDateTime(time: Long): String{

        val sdfYear = SimpleDateFormat("yyyy", Locale.getDefault())
        val postYear = sdfYear.format(Date(time*1000L))
        val currentYear = sdfYear.format(Date())

        val oldPostDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val currentYearPostDateFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())

        val postDate = if (postYear == currentYear){
            currentYearPostDateFormat.format(Date(time*1000L))
        } else {
            oldPostDateFormat.format(Date(time*1000L))
        }
        val timeFormat = DateFormat.getTimeFormat(context)
        val postTime = timeFormat.format(Date(time*1000L))

        return "$postDate,${postTime.toUpperCase(Locale.getDefault())}"
    }
}