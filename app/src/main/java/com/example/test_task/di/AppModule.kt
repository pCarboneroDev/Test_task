package com.example.test_task.di

import com.example.data.GtfsDatasource
import com.example.data.gtfsFileSource.GtfsFileSource
import com.example.data.repositoriesImpl.BusStopRepositoryImpl
import com.example.data.repositoriesImpl.DepartureRepositoryImpl
import com.example.domain.repositories.BusStopRepository
import com.example.domain.repositories.DepartureRepository
import com.example.domain.usecases.GetNextThreeDeparturesUseCase
import com.example.domain.usecases.GetTargetStopUseCase
import com.example.test_task.data.GtfsFileSourceAndroid
import com.example.test_task.presentation.staticBusStopInfo.StaticBusStopInfoViewmodel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<GtfsFileSource>{ GtfsFileSourceAndroid(androidContext()) }
    single<GtfsDatasource> { GtfsDatasource(get()) }

    single<DepartureRepository> { DepartureRepositoryImpl(get()) }
    single<BusStopRepository> { BusStopRepositoryImpl(get()) }

    single<GetNextThreeDeparturesUseCase> { GetNextThreeDeparturesUseCase(get()) }
    single<GetTargetStopUseCase> { GetTargetStopUseCase(get()) }

    single<StaticBusStopInfoViewmodel> { StaticBusStopInfoViewmodel(get(), get()) }
}