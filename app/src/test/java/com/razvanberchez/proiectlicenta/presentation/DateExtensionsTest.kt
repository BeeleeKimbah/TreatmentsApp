package com.razvanberchez.proiectlicenta.presentation

import com.google.common.truth.Truth.assertThat
import com.razvanberchez.proiectlicenta.data.model.TimeSlot
import org.junit.Before
import org.junit.Test
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.TimeZone

class DateExtensionsTest {
    private lateinit var calendar: Calendar
    private lateinit var timeSlot: TimeSlot

    @Before
    fun setUp() {
        calendar = GregorianCalendar(TimeZone.getDefault())
        timeSlot = TimeSlot(8, 0)
    }

    @Test
    fun addTimeSlot_SameDay() = run {
        calendar.set(Calendar.YEAR, 2001)
        calendar.set(Calendar.DATE, 17)
        calendar.set(Calendar.MONTH, 3)
        calendar.set(Calendar.HOUR_OF_DAY, 4)
        calendar.time = calendar.time.addTimeSlot(timeSlot)
        assertThat(calendar.get(Calendar.DATE)).isEqualTo(17)
    }

    @Test
    fun addTimeSlot_IncrementDay() = run {
        calendar.set(Calendar.YEAR, 2001)
        calendar.set(Calendar.DATE, 17)
        calendar.set(Calendar.MONTH, 3)
        calendar.set(Calendar.HOUR_OF_DAY, 18)
        calendar.time = calendar.time.addTimeSlot(timeSlot)
        assertThat(calendar.get(Calendar.DATE)).isEqualTo(18)
    }

    @Test
    fun addTimeSlot_IncrementYear() = run {
        calendar.set(Calendar.YEAR, 2001)
        calendar.set(Calendar.DATE, 31)
        calendar.set(Calendar.MONTH, 11)
        calendar.set(Calendar.HOUR_OF_DAY, 18)
        calendar.time = calendar.time.addTimeSlot(timeSlot)
        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2002)
    }

    @Test
    fun getYearOfNextDay_SameYear() = run {
        calendar.set(Calendar.YEAR, 2001)
        calendar.set(Calendar.DATE, 17)
        calendar.set(Calendar.MONTH, 3)
        assertThat(calendar.time.getYearOfNextDay()).isEqualTo(2001)
    }

    @Test
    fun getYearOfNextDay_IncrementYear() = run {
        calendar.set(Calendar.YEAR, 2001)
        calendar.set(Calendar.DATE, 31)
        calendar.set(Calendar.MONTH, 11)
        assertThat(calendar.time.getYearOfNextDay()).isEqualTo(2002)
    }

    @Test
    fun getNextDay_SameMonth() = run {
        calendar.set(Calendar.YEAR, 2001)
        calendar.set(Calendar.DATE, 17)
        calendar.set(Calendar.MONTH, 3)
        calendar.time = calendar.time.getNextDay()
        assertThat(calendar.get(Calendar.MONTH)).isEqualTo(3)
    }

    @Test
    fun getNextDay_IncrementMonth() = run {
        calendar.set(Calendar.YEAR, 2001)
        calendar.set(Calendar.MONTH, 3)
        calendar.set(Calendar.DATE, 30)
        calendar.set(Calendar.HOUR_OF_DAY, 16)
        calendar.time = calendar.time.getNextDay()

        assertThat(calendar.get(Calendar.DATE)).isEqualTo(1)
        assertThat(calendar.get(Calendar.MONTH)).isEqualTo(4)
    }

    @Test
    fun getNextDay_SameYear() = run {
        calendar.set(Calendar.YEAR, 2001)
        calendar.set(Calendar.DATE, 30)
        calendar.set(Calendar.MONTH, 3)
        calendar.time = calendar.time.getNextDay()
        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2001)
    }

    @Test
    fun getNextDay_IncrementYear() = run {
        calendar.set(Calendar.YEAR, 2001)
        calendar.set(Calendar.DATE, 31)
        calendar.set(Calendar.MONTH, 11)
        calendar.time = calendar.time.getNextDay()
        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2002)
    }

}