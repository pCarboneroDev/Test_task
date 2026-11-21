package com.example.domain.usecases

import com.example.domain.entities.Calendar
import com.example.domain.entities.CalendarDate
import com.example.domain.entities.Departure
import com.example.domain.entities.Route
import com.example.domain.entities.StopTime
import com.example.domain.entities.Trip
import com.example.domain.entities.params.NextThreeDeparturesParams
import com.example.domain.repositories.DepartureRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDate.parse
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class GetNextThreeDeparturesUseCaseTest {
    private val repo: DepartureRepository = mockk<DepartureRepository>()
    private val useCase: GetNextThreeDeparturesUseCase = GetNextThreeDeparturesUseCase(repo)

    private val params = NextThreeDeparturesParams(
        308,
        LocalTime.parse("12:00:00"),
        parse(
            "19/11/2026",
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
        )
    )

    private val calendars = listOf<Calendar>(
        Calendar(
            serviceId = "service1",
            monday = true,
            tuesday = true,
            wednesday = true,
            thursday = true,
            friday = true,
            saturday = true,
            sunday = true,
            startDate = "20260101",
            endDate = "20271231"
        )
    )

    private val trips = listOf<Trip>(
        Trip(
            tripId = "trip1",
            routeId = 1,
            serviceId = "service1"
        )
    )


    @Test
    fun shouldReturnEmptyListWhenNoAvailableTrips() = runTest {
        coEvery { repo.getCalendars() } returns emptyList()
        coEvery { repo.getCalendarDates() } returns emptyList()
        coEvery { repo.getTripsByServiceIds(any()) } returns emptyList()

        val result = useCase.call(params)

        assertTrue(result.isEmpty())
    }

    @Test
    fun shouldReturnEmptyListWhenNoFutureDepartures() = runTest {
        coEvery { repo.getCalendars() } returns calendars

        coEvery { repo.getCalendarDates() } returns emptyList()

        coEvery {
            repo.getTripsByServiceIds(any())
        } returns trips

        coEvery {
            repo.getFilteredStopTimes(any(), any())
        } returns listOf(
            StopTime(
                tripId = "trip1",
                departureTime = "08:00:00",
                stopId = 308,
                arrivalTime = "08:00:00",
            )
        )

        val result = useCase.call(params)


        assertTrue(result.isEmpty())
    }

    @Test
    fun shouldReturnOnlyNextThreeDepartures() = runTest {
        coEvery { repo.getCalendars() } returns calendars

        coEvery { repo.getCalendarDates() } returns emptyList()

        coEvery {
            repo.getTripsByServiceIds(any())
        } returns trips

        coEvery {
            repo.getFilteredStopTimes(any(), any())
        } returns listOf(
            StopTime(
                tripId = "trip1",
                departureTime = "18:00:00",
                stopId = 308,
                arrivalTime = "18:00:00",
            ),
            StopTime(
                tripId = "trip1",
                departureTime = "18:05:00",
                stopId = 308,
                arrivalTime = "18:05:00",
            ),
            StopTime(
                tripId = "trip1",
                departureTime = "18:10:00",
                stopId = 308,
                arrivalTime = "18:10:00",
            ),
            StopTime(
                tripId = "trip1",
                departureTime = "18:15:00",
                stopId = 308,
                arrivalTime = "18:15:00",
            )
        )

        coEvery { repo.getAllRoutes() } returns listOf(
            Route(
                routeId = 1,
                "agency1",
                routeType = 1
            )
        )

        val result = useCase.call(params)

        assertTrue(result.size == 3)
    }

    @Test
    fun shouldReturnDeparturesAfterCurrentTime() = runTest {
        coEvery { repo.getCalendars() } returns calendars

        coEvery { repo.getCalendarDates() } returns emptyList()

        coEvery {
            repo.getTripsByServiceIds(any())
        } returns trips

        coEvery {
            repo.getFilteredStopTimes(any(), any())
        } returns listOf(
            StopTime(
                tripId = "trip1",
                departureTime = "10:00:00",
                stopId = 308,
                arrivalTime = "10:00:00",
            ),
            StopTime(
                tripId = "trip1",
                departureTime = "18:05:00",
                stopId = 308,
                arrivalTime = "18:05:00",
            ),
            StopTime(
                tripId = "trip1",
                departureTime = "18:10:00",
                stopId = 308,
                arrivalTime = "18:10:00",
            ),
            StopTime(
                tripId = "trip1",
                departureTime = "18:15:00",
                stopId = 308,
                arrivalTime = "18:15:00",
            )
        )

        coEvery { repo.getAllRoutes() } returns listOf(
            Route(
                routeId = 1,
                "agency1",
                routeType = 1
            )
        )

        val result = useCase.call(params)

        result.forEach { departure ->
            assertTrue(LocalTime.parse(departure.departureTime).isAfter(params.currentTime))
        }
    }

    @Test
    fun returnAllDeparturesWhenThereAreLessThanThree() = runTest {
        coEvery { repo.getCalendars() } returns calendars

        coEvery { repo.getCalendarDates() } returns emptyList()

        coEvery {
            repo.getTripsByServiceIds(any())
        } returns trips

        coEvery {
            repo.getFilteredStopTimes(any(), any())
        } returns listOf(
            StopTime(
                tripId = "trip1",
                departureTime = "18:10:00",
                stopId = 308,
                arrivalTime = "18:10:00",
            ),
            StopTime(
                tripId = "trip1",
                departureTime = "18:15:00",
                stopId = 308,
                arrivalTime = "18:15:00",
            )
        )

        coEvery { repo.getAllRoutes() } returns listOf(
            Route(
                routeId = 1,
                "agency1",
                routeType = 1
            )
        )

        val result = useCase.call(params)

        assertTrue(result.size == 2)
    }

    @Test
    fun excludesExceptionInCalendarDates() = runTest{
        coEvery { repo.getCalendars() } returns calendars

        coEvery { repo.getCalendarDates() } returns listOf(
            CalendarDate(
                serviceId = "service1",
                date = "20261119",
                exceptionType = 2
            )
        )

        coEvery {
            repo.getTripsByServiceIds(any())
        } returns emptyList()


        val result = useCase.call(params)

        assertTrue(result.isEmpty())
    }

    @Test
    fun includesExceptionServicesInCalendarDates() = runTest{
        coEvery { repo.getCalendars() } returns calendars

        coEvery { repo.getCalendarDates() } returns listOf(
            CalendarDate(
                serviceId = "service1",
                date = "20261119",
                exceptionType = 1
            )
        )

        coEvery {
            repo.getTripsByServiceIds(any())
        } returns trips

        coEvery {
            repo.getFilteredStopTimes(any(), any())
        } returns listOf(
            StopTime(
                tripId = "trip1",
                departureTime = "18:05:00",
                stopId = 308,
                arrivalTime = "18:05:00",
            ),
            StopTime(
                tripId = "trip1",
                departureTime = "18:10:00",
                stopId = 308,
                arrivalTime = "18:10:00",
            ),
            StopTime(
                tripId = "trip1",
                departureTime = "18:15:00",
                stopId = 308,
                arrivalTime = "18:15:00",
            )
        )

        coEvery { repo.getAllRoutes() } returns listOf(
            Route(
                routeId = 1,
                "agency1",
                routeType = 1
            )
        )

        val result = useCase.call(params)

        assertTrue(result.size == 3)
    }
}