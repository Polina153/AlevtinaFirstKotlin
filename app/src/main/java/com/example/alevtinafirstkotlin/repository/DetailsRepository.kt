package com.example.alevtinafirstkotlin.repository

import okhttp3.Callback

interface DetailsRepository {
    fun getWeatherDetailsFromServer(requestLink: String, callback: Callback)
}