package com.example.test_task.presentation.staticBusStopInfo



import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.test_task.presentation.staticBusStopInfo.composables.CenteredElement
import com.example.test_task.presentation.staticBusStopInfo.views.StopDepartures
import com.example.test_task.utils.UiStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.koin.androidx.compose.koinViewModel
import java.time.LocalTime
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun StaticBusStopInfoScreen(modifier: Modifier) {
    val vm: StaticBusStopInfoViewmodel = koinViewModel()

    val uiState by vm.uiState.collectAsState()
    val stop by vm.currentStop.collectAsState()
    val departures by vm.departures.collectAsState()

    /**
     * Currently, the CSV files are read every time information is needed.
     * For this approach, I assumed that all the information would be read only once (for features 1 and 2)
     * Although this is a simple enhance to keep the data updated in screen, the ideal approach
     * would be to read the CSV files only once and store the data in memory.
     */
    if(departures.isNotEmpty()){
        val firstDepartureTime = LocalTime.parse(departures[0].departureTime)
        LaunchedEffect(firstDepartureTime) {
            while (isActive) {
                delay(60_000L.milliseconds)
                if(LocalTime.now().isAfter(firstDepartureTime))
                    vm.loadData()
            }
        }
    }

    when (uiState) {
        UiStatus.IDLE -> {
            CenteredElement { Text("Idle") }
        }
        UiStatus.LOADING -> {
            CenteredElement { CircularProgressIndicator() }
        }
        UiStatus.ERROR -> {
            CenteredElement { Text("An error has occurred. Try restarting the app") }
        }
        UiStatus.SUCCESS -> {
            StopDepartures(stop, departures, modifier)
        }
    }
}