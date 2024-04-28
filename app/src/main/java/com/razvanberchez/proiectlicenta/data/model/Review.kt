package com.razvanberchez.proiectlicenta.data.model

enum class Score(val value: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5)
}

data class Review(
    val text: String,
    val score: Score
)
