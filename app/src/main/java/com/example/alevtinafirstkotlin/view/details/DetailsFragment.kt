package com.example.alevtinafirstkotlin.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.alevtinafirstkotlin.R
import com.example.alevtinafirstkotlin.databinding.FragmentDetailsBinding
import com.example.alevtinafirstkotlin.databinding.FragmentMainBinding
import com.example.alevtinafirstkotlin.model.Weather
import com.example.alevtinafirstkotlin.viewmodel.MainViewModel

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

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
        arguments?.getParcelable<Weather>(BUNDLE_EXTRA)?.let { weather ->
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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

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