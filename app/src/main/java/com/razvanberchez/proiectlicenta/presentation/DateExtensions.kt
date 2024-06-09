package com.razvanberchez.proiectlicenta.presentation

import com.razvanberchez.proiectlicenta.data.model.TimeSlot
import java.text.DateFormat.getDateInstance
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone

const val DAY_MILLIS = 86400000
const val HOUR_MILLIS = 3600000
const val MIN_MILLIS = 60000

fun timeZoneOffset(): Int {
    val tz = TimeZone.getDefault()
    val cal = GregorianCalendar.getInstance(tz)
    return tz.getOffset(cal.getTimeInMillis())
}

fun Date.format(): String {
    val d = getDateInstance()
    return d.format(this)
}

fun Date.addTimeSlot(timeSlot: TimeSlot): Date {
    return Date(
        this.time
                + (timeSlot.first) * HOUR_MILLIS
                + timeSlot.second * MIN_MILLIS
    )
}

fun Date.toUTC(): Date {
    return Date(this.time - timeZoneOffset())
}

fun Date.getDateWithoutTime(): Date {
    val cal = GregorianCalendar()
    cal.time = Date(this.time)
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return Date(cal.time.time + timeZoneOffset())
}

fun Date.getNextDay(): Date {
    val cal = GregorianCalendar()
    cal.time = Date(this.time)
    cal.add(Calendar.DATE, 1)
    return Date(cal.time.time + timeZoneOffset())
}

fun Date.getYearOfNextDay(): Int {
    val cal = GregorianCalendar()
    cal.time = this.getNextDay()
    return cal.get(Calendar.YEAR)
}