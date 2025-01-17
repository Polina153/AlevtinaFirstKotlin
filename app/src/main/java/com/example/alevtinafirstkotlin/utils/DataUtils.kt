package com.example.alevtinafirstkotlin.utils

import com.example.alevtinafirstkotlin.model.FactDTO
import com.example.alevtinafirstkotlin.model.Weather
import com.example.alevtinafirstkotlin.model.WeatherDTO
import com.example.alevtinafirstkotlin.model.getDefaultCity

fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact: FactDTO = weatherDTO.fact!!
    return listOf(Weather(getDefaultCity(), fact.temp!!, fact.feels_like!!, fact.condition!!/*, fact.icon*/))
}