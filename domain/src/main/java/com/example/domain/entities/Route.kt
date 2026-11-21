package com.example.domain.entities

data class Route(
    val routeId: Int,
    val agencyId: String,
    val routeShortName: String? = null,
    val routeLongName: String? = null,
    val routeDesc: String? = null,
    val routeType: Int,
    val routeUrl: String? = null,
    val routeColor: String? = null,
    val routeTextColor: String? = null,
    val routeSortOrder: Int? = null,
    val continuousPickup: Int? = null,
    val continuousDropOff: Int? = null
){
    companion object {
        fun fromList(values: List<String>): Route {
            return Route(
                values[0].toInt(),
                values[1],
                values[2],
                values[3],
                values[4],
                values[5].toInt(),
                values[6],
                values[7],
                values[8],
                values[9].toInt(),
                values[10].toInt(),
                values[11].toInt()
            )
        }
    }
}