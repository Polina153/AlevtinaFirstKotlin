package com.example.alevtinafirstkotlin.repository

import com.example.alevtinafirstkotlin.model.WeatherDTO

import okhttp3.Callback

interface DetailsRepository {
   // fun getWeatherDetailsFromServer(requestLink: String, callback: Callback)
   fun getWeatherDetailsFromServer(
       lat: Double,
       lon: Double,
       callback: retrofit2.Callback<WeatherDTO>
   )
}