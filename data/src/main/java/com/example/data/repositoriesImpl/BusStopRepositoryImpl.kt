package com.example.data.repositoriesImpl

import com.example.data.GtfsDatasource
import com.example.domain.entities.Stop
import com.example.domain.repositories.BusStopRepository

class BusStopRepositoryImpl(
    private val gtfsDatasource: GtfsDatasource
): BusStopRepository {
    override suspend fun getTargetStop(stopId: Int): Stop? {
        return gtfsDatasource.getStopById(stopId)
    }
}