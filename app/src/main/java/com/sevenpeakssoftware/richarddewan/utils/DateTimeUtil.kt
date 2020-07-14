package com.sevenpeakssoftware.richarddewan.utils

import android.content.Context
import android.text.format.DateFormat
import com.sevenpeakssoftware.richarddewan.di.scope.ActivityScope
import java.text.SimpleDateFormat
import java.util.*


@ActivityScope
class DateTimeUtil (private val context: Context) {

    fun getDateTime(date: Date): String{

        val postCalender = Calendar.getInstance().apply {
            time = date
        }
        val currentCalender = Calendar.getInstance().apply {
            time = Date()
        }
        val postYear = postCalender.get(Calendar.YEAR)
        val currentYear = currentCalender.get(Calendar.YEAR)


        val oldPostDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val currentYearPostDateFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())

        val postDate = if (postYear == currentYear){
            currentYearPostDateFormat.format(date)
        } else {
            oldPostDateFormat.format(date)
        }
        val timeFormat = DateFormat.getTimeFormat(context)
        val postTime = timeFormat.format(date)

        return "$postDate,${postTime.toUpperCase(Locale.getDefault())}"
    }
}