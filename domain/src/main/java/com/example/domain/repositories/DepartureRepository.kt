package com.example.domain.repositories

import com.example.domain.entities.Calendar
import com.example.domain.entities.CalendarDate
import com.example.domain.entities.Route
import com.example.domain.entities.Stop
import com.example.domain.entities.StopTime
import com.example.domain.entities.Trip

interface DepartureRepository {
    suspend fun getStopById(stopId: Int): Stop?
    suspend fun getTripsByServiceIds(serviceIds: List<String>): List<Trip>
    suspend fun getRouteById(routeId: Int): Route
    suspend fun getFilteredStopTimes(targetStopId: Int, tripIds: List<String>): List<StopTime>
    suspend fun getCalendars(): List<Calendar>
    suspend fun getCalendarDates(): List<CalendarDate>
    suspend fun getAllRoutes(): List<Route>
}