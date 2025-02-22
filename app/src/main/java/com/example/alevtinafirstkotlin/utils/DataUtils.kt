package com.example.alevtinafirstkotlin.utils

import com.example.alevtinafirstkotlin.model.City
import com.example.alevtinafirstkotlin.model.FactDTO
import com.example.alevtinafirstkotlin.model.Weather
import com.example.alevtinafirstkotlin.model.WeatherDTO
import com.example.alevtinafirstkotlin.model.getDefaultCity
import com.example.alevtinafirstkotlin.room.HistoryEntity

fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact: FactDTO = weatherDTO.fact!!
    return listOf(
        Weather(
            getDefaultCity(),
            fact.temp!!,
            fact.feels_like!!,
            fact.condition!!,
            fact.icon
        )
    )
}

fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
    return entityList.map {
        Weather(City(it.city, 0.0, 0.0), it.temperature, 0, it.condition)
    }
}

fun convertWeatherToEntity(weather: Weather): HistoryEntity {
    return HistoryEntity(0, weather.city.city, weather.temperature, weather.condition)
}

