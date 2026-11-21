package com.example.domain.entities

data class Trip(
    val routeId: Int,
    val serviceId: String,
    val tripId: String,
    val tripHeadsign: String? = null,
    val tripShortName: String? = null,
    val directionId: Int? = null,
    val blockId: String? = null,
    val shapeId: String? = null,
    val wheelchairAccessible: Int = 0,
    val bikesAllowed: Int = 0
){
    companion object {
        fun fromList(values: List<String>): Trip {
            return Trip(
                values[0].toInt(),
                values[1],
                values[2],
                values[3],
                values[4],
                values[5].toInt(),
                values[6],
                values[7],
                values[8].toInt(),
                values[9].toInt(),
            )
        }
    }
}