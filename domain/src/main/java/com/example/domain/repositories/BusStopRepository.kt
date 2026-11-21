package com.example.domain.repositories

import com.example.domain.entities.Stop

interface BusStopRepository {
    suspend fun getTargetStop(stopId: Int): Stop?
}