package com.example.alevtinafirstkotlin.view

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.alevtinafirstkotlin.R
import com.example.alevtinafirstkotlin.databinding.FragmentMainBinding
import com.example.alevtinafirstkotlin.model.Weather
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
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
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        //viewModel.getWeather()
        viewModel.getWeatherFromLocalSource()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            /*is AppState.Success -> {
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
                }*/
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
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    companion object {
        fun newInstance() = MainFragment()
    }
}