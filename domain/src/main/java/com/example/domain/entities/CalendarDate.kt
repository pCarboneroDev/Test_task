package com.example.domain.entities

import java.time.LocalDate

data class CalendarDate(
    val serviceId: String,
    val date: String,
    val exceptionType: Int
) {
    companion object {
        fun fromList(values: List<String>): CalendarDate {
            return CalendarDate(
                values[0],
                values[1],
                values[2].toInt()
            )
        }
    }
}