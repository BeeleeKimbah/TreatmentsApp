package com.razvanberchez.proiectlicenta.data.model

typealias TimeSlot = Pair<Int, Int>

val allSlots = setOf(
    Pair(8, 0),
    Pair(8, 30),
    Pair(9, 0),
    Pair(9, 30),
    Pair(10, 0),
    Pair(10, 30),
    Pair(11, 0),
    Pair(11, 30),
    Pair(12, 0),
    Pair(12, 30),
    Pair(13, 0),
    Pair(13, 30),
    Pair(14, 0),
    Pair(14, 30),
    Pair(15, 0),
    Pair(15, 30),
    Pair(16, 0),
    Pair(16, 30)
)

fun TimeSlot.format(): String {
    return if (this.second == 0)
        "${this.first}:00"
    else
        "${this.first}:${this.second}"
}