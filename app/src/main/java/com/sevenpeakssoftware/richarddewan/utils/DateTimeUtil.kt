package com.sevenpeakssoftware.richarddewan.utils

import android.content.Context
import android.text.format.DateFormat
import com.sevenpeakssoftware.richarddewan.di.scope.ActivityScope
import java.text.SimpleDateFormat
import java.util.*


@ActivityScope
class DateTimeUtil (private val context: Context) {

    fun getDateTime(time: Int): String{

        val sdfYear = SimpleDateFormat("yyyy", Locale.getDefault())
        val postYear = sdfYear.format(time)
        val currentYear = sdfYear.format(Date())

        val oldPostDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val currentYearPostDateFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())

        val postDate = if (postYear == currentYear){
            currentYearPostDateFormat.format(time)
        } else {
            oldPostDateFormat.format(time)
        }
        val timeFormat = DateFormat.getTimeFormat(context)
        val postTime = timeFormat.format(time)

        return "$postDate,${postTime.toUpperCase(Locale.getDefault())}"
    }
}