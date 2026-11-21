package com.example.domain.usecases

import com.example.domain.entities.Stop
import com.example.domain.repositories.BusStopRepository

class GetTargetStopUseCase(
    private val busSTopRepository: BusStopRepository
): UseCase<Int, Stop?> {
    override suspend fun call(param: Int): Stop? {
        return busSTopRepository.getTargetStop(param)
    }
}