package com.example.test_task.presentation.staticBusStopInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.Departure
import com.example.domain.entities.Stop
import com.example.domain.entities.params.NextThreeDeparturesParams
import com.example.domain.usecases.GetNextThreeDeparturesUseCase
import com.example.domain.usecases.GetTargetStopUseCase
import com.example.test_task.utils.UiStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class StaticBusStopInfoViewmodel(
    private val getNextThreeDeparturesUseCase: GetNextThreeDeparturesUseCase,
    private val getTargetStopUseCase: GetTargetStopUseCase,
): ViewModel() {
    private val targetStopId = 308 // stopId of Plaza Eliptica 4

    private val _uiState = MutableStateFlow(UiStatus.IDLE)
    val uiState: StateFlow<UiStatus> = _uiState.asStateFlow()

    private val _currentStop = MutableStateFlow(
        Stop(
            stopId = 308,
            stopCode = 123,
            stopName = "Prado de San Sebastián",
            stopDesc = "",
            stopLat = 41.214214,
            stopLon = 41.21412,
            zoneId = 1,
            locationType = 1,
            wheelchairBoarding = 1
        )
    )
    val currentStop: StateFlow<Stop> = _currentStop.asStateFlow()

    private val _departures = MutableStateFlow<List<Departure>>(emptyList())
    val departures: StateFlow<List<Departure>> = _departures.asStateFlow()


    init {
        viewModelScope.launch {
            loadData()
        }
    }

    suspend fun loadData(){
        try{
            _uiState.value = UiStatus.LOADING

            val targetStop = getTargetStopUseCase.call(targetStopId)

            _currentStop.value = targetStop!!

            _departures.value = getNextThreeDeparturesUseCase.call(
                NextThreeDeparturesParams(
                    targetStopId,
                    currentTime = LocalTime.now(),
                    date = LocalDate.now()
                )
            )

            _uiState.value = UiStatus.SUCCESS
        }
        catch(e: Exception){
            _uiState.value = UiStatus.ERROR
        }
    }
}