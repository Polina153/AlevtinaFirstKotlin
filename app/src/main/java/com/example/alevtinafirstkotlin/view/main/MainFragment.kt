package com.example.alevtinafirstkotlin.view.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.alevtinafirstkotlin.R
import com.example.alevtinafirstkotlin.databinding.FragmentMainBinding
import com.example.alevtinafirstkotlin.model.Weather
import com.example.alevtinafirstkotlin.view.details.DetailsFragment
import com.example.alevtinafirstkotlin.viewmodel.AppState
import com.example.alevtinafirstkotlin.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var isDataSetWorld: Boolean = false

    private var isDataSetRus: Boolean = true

    private val viewModel: MainViewModel by lazy{
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private val adapter = MainFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            activity?.supportFragmentManager?.apply{
                beginTransaction()
                .add(R.id.container, DetailsFragment.newInstance(Bundle().apply {
                    putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)
                }))
                .addToBackStack("")
                .commitAllowingStateLoss()
            }
        }
    })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener { changeWeatherDataSet() }
        //viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        showListOfTowns()
    }

    private fun showListOfTowns() {
        activity?.let {
            if (it.getPreferences(Context.MODE_PRIVATE).getBoolean(IS_WORLD_KEY, false)) {
                changeWeatherDataSet()
            } else {
                viewModel.getWeatherFromLocalSourceRus()
            }
        }
    }

    private fun changeWeatherDataSet() {
        if (isDataSetWorld) {
            viewModel.getWeatherFromLocalSourceRus()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        } else {
            viewModel.getWeatherFromLocalSourceWorld()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        }
        isDataSetWorld = !isDataSetWorld

        saveListOfTowns(isDataSetWorld)
    }

    private fun saveListOfTowns(isDataSetWorld: Boolean) {
        activity?.let {
            with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                putBoolean(IS_WORLD_KEY, isDataSetWorld)
                apply()
            }
        }
    }

    /*  private fun changeWeatherDataSet() {
    if (isDataSetRus) {
           viewModel.getWeatherFromLocalSourceWorld()
           binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
       } else {
           viewModel.getWeatherFromLocalSourceRus()
           binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
       }.also{
           isDataSetRus = !isDataSetRus
       }

   }*/

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeather(appState.weatherData)
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                binding.itemContainer.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getWeatherFromLocalSourceRus() })

                /*Snackbar
                    .make(binding.mainFragmentFAB, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.reload)) { viewModel.getWeatherFromLocalSourceRus() }
                    .show()*/
            }
        }
    }
    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }

    companion object {
        fun newInstance() =
            MainFragment()

        private const val IS_WORLD_KEY = "LIST_OF_TOWNS_KEY"
    }
}

