package com.example.domain.entities

data class StopTime(
    val tripId: String,
    val arrivalTime: String,
    val departureTime: String,
    val stopId: Int,
    val stopSequence: Int = 0,
    val stopHeadsign: String? = null,
    val pickupType: Int = 0,
    val dropOffType: Int = 0,
    val continuousPickup: Int? = null,
    val continuousDropOff: Int? = null,
    val timepoint: Int? = null
){
    companion object {
        fun fromList(values: List<String>): StopTime {
            return StopTime(
                values[0],
                values[1],
                values[2],
                values[3].toInt(),
                values[4].toInt(),
                values[5],
                values[6].toInt(),
                values[7].toInt(),
                values[8].toInt(),
                values[9].toInt(),
                values[10].toInt(),
            )
        }
    }
}