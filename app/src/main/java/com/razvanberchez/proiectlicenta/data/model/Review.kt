package com.razvanberchez.proiectlicenta.data.model

enum class Score(val value: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    companion object{
        fun getScore(intValue: Int): Score? {
            return when(intValue) {
                1 -> ONE
                2 -> TWO
                3 -> THREE
                4 -> FOUR
                5 -> FIVE
                else -> null
            }
        }
    }

}

data class Review(
    val text: String,
    val score: Score
)
