package com.razvanberchez.proiectlicenta.presentation

import java.text.DateFormat.getDateInstance
import java.util.Date

fun Date.format(): String {
    val d = getDateInstance()
    return d.format(this)
}