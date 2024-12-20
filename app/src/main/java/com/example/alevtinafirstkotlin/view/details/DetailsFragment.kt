package com.example.alevtinafirstkotlin.view.details

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.alevtinafirstkotlin.R
import com.example.alevtinafirstkotlin.databinding.FragmentDetailsBinding
import com.example.alevtinafirstkotlin.databinding.FragmentMainBinding
import com.example.alevtinafirstkotlin.model.Weather
import com.example.alevtinafirstkotlin.model.WeatherDTO
import com.example.alevtinafirstkotlin.viewmodel.MainViewModel
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle: Weather
    private val onLoadListener: WeatherLoader.WeatherLoaderListener =
        object : WeatherLoader.WeatherLoaderListener {

            override fun onLoaded(weatherDTO: WeatherDTO) {
                displayWeather(weatherDTO)
            }

            override fun onFailed(throwable: Throwable) {
                //Обработка ошибки
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val observer = Observer<Any> { renderData(it) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
    }

    private fun renderData(data: Any) {
        Toast.makeText(context, data.toString(), Toast.LENGTH_LONG).show()
    }*/
    /* override fun onActivityCreated(savedInstanceState: Bundle?) {
         super.onActivityCreated(savedInstanceState)
         viewModel = ViewModelProvider(this)[MainViewModel::class.java]
         viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
         //viewModel.getWeather()
         viewModel.getWeatherFromLocalSource()
     }

     private fun renderData(appState: AppState) {
         when (appState) {
             is AppState.Success -> {
                     val weatherData = appState.weatherData
                     binding.loadingLayout.visibility = View.GONE
                     Snackbar.make(binding.mainView, "Success", Snackbar.LENGTH_LONG).show()
                 }

                 is AppState.Loading -> {
                     binding.loadingLayout.visibility = View.VISIBLE
                 }

                 is AppState.Error -> {
                     binding.loadingLayout.visibility = View.GONE
                     Snackbar
                         .make(binding.mainView, "Error", Snackbar.LENGTH_INDEFINITE)
                         .setAction("Reload") { viewModel.getWeather() }
                         .show()
                 }
             is AppState.Success -> {
                 val weatherData = appState.weatherData
                 binding.loadingLayout.visibility = View.GONE
                 setData(weatherData)
             }

             is AppState.Loading -> {
                 binding.loadingLayout.visibility = View.VISIBLE
             }

             is AppState.Error -> {
                 binding.loadingLayout.visibility = View.GONE
                 Snackbar
                     .make(binding.mainView, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
                     .setAction(getString(R.string.reload)) { viewModel.getWeatherFromLocalSource() }
                     .show()
             }
         }
     }

     private fun setData(weatherData: Weather) {
         binding.cityName.text = weatherData.city.city
         binding.cityCoordinates.text = String.format(
             getString(R.string.city_coordinates),
             weatherData.city.lat.toString(),
             weatherData.city.lon.toString()
         )
         binding.temperatureValue.text = weatherData.temperature.toString()
         binding.feelsLikeValue.text = weatherData.feelsLike.toString()
     }*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherBundle = arguments?.getParcelable<Weather>(BUNDLE_EXTRA)?: Weather()
        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
        val loader = WeatherLoader(onLoadListener, weatherBundle.city.lat, weatherBundle.city.lon)
        loader.loadWeather()


       /* .let { weather ->
            weather.city.also { city ->
                binding.cityName.text = city.city
                binding.cityCoordinates.text = String.format(
                    getString(R.string.city_coordinates),
                    city.lat.toString(),
                    city.lon.toString()
                )
                binding.temperatureValue.text = weather.temperature.toString()
                binding.feelsLikeValue.text = weather.feelsLike.toString()
            }
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayWeather(weatherDTO: WeatherDTO) {
        with(binding) {
            mainView.visibility = View.VISIBLE
            loadingLayout.visibility = View.GONE
            val city = weatherBundle.city
            cityName.text = city.city
            cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                city.lat.toString(),
                city.lon.toString()
            )
            weatherCondition.text = weatherDTO.fact?.condition
            temperatureValue.text = weatherDTO.fact?.temp.toString()
            feelsLikeValue.text = weatherDTO.fact?.feels_like.toString()
        }
    }
/*

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadWeather() {
        try {
            val uri =
                URL("https://api.weather.yandex.ru/v2/informers?lat=${weatherBundle.city.lat}&lon=${weatherBundle.city.lon}")
            val handler = Handler()
            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.addRequestProperty(
                        "X-Yandex-API-Key",
                        YOUR_API_KEY
                    )
                    urlConnection.readTimeout = 10000
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))

                    // преобразование ответа от сервера (JSON) в модель данных (WeatherDTO)
                    val weatherDTO: WeatherDTO =
                        Gson().fromJson(getLines(bufferedReader), WeatherDTO::class.java)
                    handler.post { displayWeather(weatherDTO) }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    //Обработка ошибки
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            //Обработка ошибки
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
*/

    companion object {
        //fun newInstance() = DetailsFragment()
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}