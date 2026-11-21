package com.example.data.repositoriesImpl

import com.example.data.GtfsDatasource
import com.example.domain.entities.Calendar
import com.example.domain.entities.CalendarDate
import com.example.domain.entities.Route
import com.example.domain.entities.Stop
import com.example.domain.entities.StopTime
import com.example.domain.entities.Trip
import com.example.domain.repositories.DepartureRepository

class DepartureRepositoryImpl(
    private val datasource: GtfsDatasource
) : DepartureRepository {

    override suspend fun getStopById(stopId: Int): Stop? {
        return datasource.getStopById(stopId)
    }

    override suspend fun getTripsByServiceIds(serviceIds: List<String>): List<Trip> {
        return datasource.getTripsByServiceIds(serviceIds)
    }

    override suspend fun getRouteById(routeId: Int): Route {
        return datasource.getRoute(routeId)
    }

    override suspend fun getFilteredStopTimes(targetStopId: Int, tripIds: List<String>): List<StopTime> {
        return datasource.getFilteredStopTimes(targetStopId, tripIds)
    }

    override suspend fun getCalendars(): List<Calendar> {
        return datasource.getAllCalendars()
    }

    override suspend fun getCalendarDates(): List<CalendarDate> {
        return datasource.getAllCalendarDates()
    }

    override suspend fun getAllRoutes(): List<Route> {
        return datasource.getAllRoutes()
    }
}