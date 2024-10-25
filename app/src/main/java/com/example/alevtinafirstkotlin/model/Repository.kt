package com.example.alevtinafirstkotlin.model

interface Repository {
    fun getWeatherFromServer(): Weather
    //fun getWeatherFromLocalStorage(): Weather
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>

}