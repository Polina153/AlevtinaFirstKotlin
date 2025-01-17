package com.example.alevtinafirstkotlin.repository

import com.example.alevtinafirstkotlin.model.Weather

interface Repository {
    fun getWeatherFromServer(): Weather
    //fun getWeatherFromLocalStorage(): Weather
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>

}