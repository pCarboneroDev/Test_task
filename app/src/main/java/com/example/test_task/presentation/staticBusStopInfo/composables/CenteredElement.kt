package com.example.test_task.presentation.staticBusStopInfo.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CenteredElement(element: @Composable () -> Unit){
    Box(
        Modifier.fillMaxSize(),
        Alignment.Center
    ){
        element()
    }
}