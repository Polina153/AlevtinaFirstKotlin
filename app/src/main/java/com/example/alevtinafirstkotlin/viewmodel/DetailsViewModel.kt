package com.example.alevtinafirstkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alevtinafirstkotlin.app.App.Companion.getHistoryDao
import com.example.alevtinafirstkotlin.model.Weather
import com.example.alevtinafirstkotlin.model.WeatherDTO
import com.example.alevtinafirstkotlin.repository.DetailsRepository
import com.example.alevtinafirstkotlin.repository.DetailsRepositoryImpl
import com.example.alevtinafirstkotlin.repository.LocalRepository
import com.example.alevtinafirstkotlin.repository.LocalRepositoryImpl
import com.example.alevtinafirstkotlin.repository.RemoteDataSource
import com.example.alevtinafirstkotlin.utils.convertDtoToModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsViewModel(
    internal val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource()),
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {


    fun getWeatherFromRemoteSource(lat: Double, lon: Double) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getWeatherDetailsFromServer(lat, lon, callBack)
    }

    fun saveCityToDB(weather: Weather) {
        historyRepository.saveEntity(weather)
    }

    private val callBack = object :
        Callback<WeatherDTO> {

        override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
            val serverResponse: WeatherDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: WeatherDTO): AppState {
            val fact = serverResponse.fact
            return if (fact == null || fact.temp == null || fact.feels_like == null || fact.condition.isNullOrEmpty()) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.Success(convertDtoToModel(serverResponse))
            }
        }
    }
}


/*   fun getLiveData() = detailsLiveData

   fun getWeatherFromRemoteSource(requestLink: String) {
       detailsLiveData.value = AppState.Loading
       detailsRepositoryImpl.getWeatherDetailsFromServer(requestLink, callBack)
   }

   private val callBack = object : Callback {

       @Throws(IOException::class)
       override fun onResponse(call: Call, response: Response) {
           val serverResponse: String? = response.body?.string()
           detailsLiveData.postValue(
               if (response.isSuccessful && serverResponse != null) {
                   checkResponse(serverResponse)
               } else {
                   AppState.Error(Throwable(SERVER_ERROR))
               }
           )
       }

       override fun onFailure(call: Call, e: IOException) {
           detailsLiveData.postValue(AppState.Error(Throwable(e.message ?: REQUEST_ERROR)))
       }

       private fun checkResponse(serverResponse: String): AppState {
           val weatherDTO: WeatherDTO =
               Gson().fromJson(serverResponse, WeatherDTO::class.java)
           val fact = weatherDTO.fact
           return if (fact == null || fact.temp == null || fact.feels_like == null || fact.condition.isNullOrEmpty()) {
               AppState.Error(Throwable(CORRUPTED_DATA))
           } else {
               AppState.Success(convertDtoToModel(weatherDTO))
           }
       }

       fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
           val fact: FactDTO = weatherDTO.fact!!
           return listOf(
               Weather(
                   getDefaultCity(),
                   fact.temp!!,
                   fact.feels_like!!,
                   fact.condition!!
               )
           )
       }

   }
}*/