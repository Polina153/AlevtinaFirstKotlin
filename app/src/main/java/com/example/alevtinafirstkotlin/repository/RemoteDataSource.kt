package com.example.alevtinafirstkotlin.repository

import com.example.alevtinafirstkotlin.view.details.MY_API_KEY

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

const val REQUEST_API_KEY = "X-Yandex-API-Key"

class RemoteDataSource {

    fun getWeatherDetails(requestLink: String, callback: Callback) {
        val builder: Request.Builder = Request.Builder().apply {
            header(REQUEST_API_KEY, MY_API_KEY)
            url(requestLink)
        }
        OkHttpClient().newCall(builder.build()).enqueue(callback)
    }

}