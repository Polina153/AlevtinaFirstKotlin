package com.example.alevtinafirstkotlin.viewmodel

import com.example.alevtinafirstkotlin.model.Weather

sealed class AppState {
    //data class Success(val weatherData: Weather) : AppState()
    data class Success(val weatherData: List<Weather>) : AppState()

    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
