package com.example.alevtinafirstkotlin.model

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