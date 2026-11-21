package com.example.data


import com.example.data.gtfsFileSource.GtfsFileSource
import com.example.domain.entities.Calendar
import com.example.domain.entities.CalendarDate
import com.example.domain.entities.Route
import com.example.domain.entities.Stop
import com.example.domain.entities.StopTime
import com.example.domain.entities.Trip
import org.apache.commons.csv.CSVFormat

class GtfsDatasource(
    private val source: GtfsFileSource
) {
    //    private val reader = csvReader()
    private val csvReader = CSVFormat.Builder.create(CSVFormat.DEFAULT)

    fun getAllCalendars(): List<Calendar> {
        return csvReader.get().parse(source.open("calendar.txt").reader())
            .drop(1)
            .map { Calendar.fromList(it.toList()) }.toList()
    }

    fun getAllCalendarDates(): List<CalendarDate> {
        return csvReader.get().parse(source.open("calendar_dates.txt").reader())
            .drop(1)
            .map { CalendarDate.fromList(it.toList()) }.toList()
    }

    fun getTripsByServiceIds(serviceIds: List<String>): List<Trip> {
        return csvReader.get().parse(source.open("trips.txt").reader())
            .drop(1)
            .map { Trip.fromList(it.toList()) }
            .filter { it.serviceId in serviceIds }
            .toList()
    }

    fun getStopById(stopId: Int): Stop? {
        return csvReader.get().parse(source.open("stops.txt").reader())
            .drop(1)
            .map {
                Stop.fromList(it.toList())
            }.firstOrNull { it.stopId == stopId }
    }

    fun getFilteredStopTimes(
        targetStopId: Int,
        tripIds: List<String>
    ): List<StopTime> {
        return csvReader.get().parse(source.open("stop_times.txt").reader())
            .drop(1)
            .map { StopTime.fromList(it.toList()) }
            .filter { it.stopId == targetStopId && tripIds.contains((it.tripId)) }.toList()
    }

    fun getAllRoutes(): List<Route> {
        return csvReader.get().parse(source.open("routes.txt").reader())
            .drop(1)
            .map { Route.fromList(it.toList()) }.toList()
    }
}