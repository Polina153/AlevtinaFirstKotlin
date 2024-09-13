package com.example.alevtinafirstkotlin.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alevtinafirstkotlin.model.Repository
import com.example.alevtinafirstkotlin.model.RepositoryImpl
import java.lang.Thread.sleep

/*class MainViewModel(private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData()) :
    ViewModel() {

    fun getData(): LiveData<Any> {
        getDataFromLocalSource()
        return liveDataToObserve
    }

    private fun getDataFromLocalSource() {
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(Any())
        }.start()
    }
}*/
class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) :
    ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getWeatherFromLocalSource() = getDataFromLocalSource()

    fun getWeatherFromRemoteSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getWeatherFromLocalStorage()))
        }.start()
    }
    /* fun getWeather() = getDataFromLocalSource()

     private fun getDataFromLocalSource() {
         liveDataToObserve.value = AppState.Loading
         Thread {
             sleep(1000)
             liveDataToObserve.postValue(AppState.Success(Any()))
         }.start()
     }*/
}

