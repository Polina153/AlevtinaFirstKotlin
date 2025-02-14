package com.example.alevtinafirstkotlin.repository

import com.example.alevtinafirstkotlin.model.Weather
import com.example.alevtinafirstkotlin.room.HistoryDao
import com.example.alevtinafirstkotlin.utils.convertHistoryEntityToWeather
import com.example.alevtinafirstkotlin.utils.convertWeatherToEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao) :
    LocalRepository {

    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToEntity(weather))
    }
}