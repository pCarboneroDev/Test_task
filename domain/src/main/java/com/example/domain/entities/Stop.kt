package com.example.domain.entities

data class Stop(
    val stopId: Int,
    val stopCode: Int,
    val stopName: String,
    val stopDesc: String,
    val stopLat: Double,
    val stopLon: Double,
    val zoneId: Int,
    val locationType: Int,
    val wheelchairBoarding: Int,
){
    companion object {
        fun fromList(values: List<String>): Stop {
            require(values.size >= 9) { "Se esperaban al menos 9 valores, se recibieron ${values.size}" }
            return Stop(
                values[0].toInt(),
                values[1].toInt(),
                values[2],
                values[3],
                values[4].toDouble(),
                values[5].toDouble(),
                values[6].toInt(),
                values[7].toInt(),
                values[8].toInt()
            )
        }
    }
}