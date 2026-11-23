package com.example.domain.usecases

import com.example.domain.entities.Calendar
import com.example.domain.entities.CalendarDate
import com.example.domain.entities.Departure
import com.example.domain.entities.params.NextThreeDeparturesParams
import com.example.domain.repositories.DepartureRepository
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.collections.filter

class GetNextThreeDeparturesUseCase(
    private val repo: DepartureRepository
) : UseCase<NextThreeDeparturesParams, List<Departure>> {
    /**
     * To test:
     * Should return departures after the current time
     * Should return only the next 3 departures
     * If less than 3 departures are available after current time, return all of them
     * If availableTrips is empty return emptyList
     * If stopTimeList is empty return emptyList
     * Services inactive for exceptions should be removed
     * Services active for exceptions should be added
     */
    override suspend fun call(param: NextThreeDeparturesParams): List<Departure> {

        val serviceIds =
            getAvailableServices(LocalDate.now(), repo.getCalendars(), repo.getCalendarDates())

        val availableTrips = repo.getTripsByServiceIds(serviceIds)

        if (availableTrips.isEmpty()) {
            return emptyList()
        }

        val stopTimeList =
            repo.getFilteredStopTimes(param.busStopId, availableTrips.map { it.tripId }).filter {
                LocalTime.parse(it.departureTime).isAfter(param.currentTime)
            }.sortedBy { LocalTime.parse(it.departureTime) }.take(3)

        if (stopTimeList.isEmpty()) {
            return emptyList()
        }

        val routes = repo.getAllRoutes()

        val departures = stopTimeList.map { stopTime ->
            val trip = availableTrips.first { it.tripId == stopTime.tripId }
            val route = routes.first { trip.routeId == it.routeId }

            Departure(
                departureTime = stopTime.departureTime,
                routeName = route.routeLongName ?: "No name",
                routeShortName = route.routeShortName ?: "n/a"
            )
        }
        return departures
    }


    private fun getAvailableServices(
        today: LocalDate,
        calendarList: List<Calendar>,
        calendarDateList: List<CalendarDate>
    ): List<String> {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")

        val dayWeek = today.dayOfWeek

        val calendarListFilteredByDate = calendarList.filter { calendar ->
            LocalDate.parse(calendar.startDate, formatter).isBefore(today)
                    && LocalDate.parse(calendar.endDate, formatter).isAfter(today)
        }

        val calendarListFiltered = calendarListFilteredByDate.filter { calendar ->
            when (dayWeek) {
                DayOfWeek.MONDAY -> calendar.monday
                DayOfWeek.TUESDAY -> calendar.tuesday
                DayOfWeek.WEDNESDAY -> calendar.wednesday
                DayOfWeek.THURSDAY -> calendar.thursday
                DayOfWeek.FRIDAY -> calendar.friday
                DayOfWeek.SATURDAY -> calendar.saturday
                DayOfWeek.SUNDAY -> calendar.sunday
            }
        }

        val services = calendarListFiltered
            .map { it.serviceId }
            .toMutableSet()


        calendarDateList
            .filter { LocalDate.parse(it.date, formatter) == today }
            .forEach { calendarDate ->
                when (calendarDate.exceptionType) {
                    1 -> services.add(calendarDate.serviceId)
                    2 -> services.remove(calendarDate.serviceId)
                }
            }
        return services.toList()
    }
}