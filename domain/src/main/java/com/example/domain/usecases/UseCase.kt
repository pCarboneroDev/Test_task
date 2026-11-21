package com.example.domain.usecases

interface UseCase<Input, Output> {
    suspend fun call(param: Input): Output
}