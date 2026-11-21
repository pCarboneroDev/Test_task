package com.example.domain.entities.params

import java.time.LocalDate
import java.time.LocalTime

data class NextThreeDeparturesParams(
    val busStopId: Int,
    val currentTime: LocalTime,
    val date: LocalDate
)
