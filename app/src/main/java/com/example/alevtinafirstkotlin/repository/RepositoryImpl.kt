package com.example.alevtinafirstkotlin.repository

import com.example.alevtinafirstkotlin.model.Weather
import com.example.alevtinafirstkotlin.model.getRussianCities
import com.example.alevtinafirstkotlin.model.getWorldCities

class RepositoryImpl : Repository {

    override fun getWeatherFromServer() = Weather()

    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()


    /*    override fun getWeatherFromServer(): Weather {
            return Weather()
        }

        override fun getWeatherFromLocalStorageRus(): List<Weather> {
            return getRussianCities()
        }

        override fun getWeatherFromLocalStorageWorld(): List<Weather> {
            return getWorldCities()
        }*/

    /*override fun getWeatherFromLocalStorage(): Weather {
        return Weather()
    }*/

}