package com.example.alevtinafirstkotlin.repository

import com.example.alevtinafirstkotlin.model.Weather

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
}