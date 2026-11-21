package com.example.domain.entities

import kotlin.Boolean

data class Calendar(
    val serviceId: String,
    val monday: Boolean,
    val tuesday: Boolean,
    val wednesday: Boolean,
    val thursday: Boolean,
    val friday: Boolean,
    val saturday: Boolean,
    val sunday: Boolean,
    val startDate: String,
    val endDate: String
){
    companion object {
        fun fromList(values: List<String>): Calendar {
            return Calendar(
                serviceId = values[0],
                monday = values[1].toInt() == 1,
                tuesday = values[2].toInt() == 1,
                wednesday = values[3].toInt() == 1,
                thursday = values[4].toInt() == 1,
                friday = values[5].toInt() == 1,
                saturday = values[6].toInt() == 1,
                sunday = values[7].toInt() == 1,
                startDate = values[8],
                endDate = values[9],
            )
        }
    }
}