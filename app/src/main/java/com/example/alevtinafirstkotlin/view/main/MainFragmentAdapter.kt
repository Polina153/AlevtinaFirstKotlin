package com.example.alevtinafirstkotlin.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alevtinafirstkotlin.R
import com.example.alevtinafirstkotlin.model.Weather
import com.example.alevtinafirstkotlin.view.main.MainFragment.OnItemViewClickListener

class MainFragmentAdapter(private  var onItemViewClickListener: OnItemViewClickListener?) : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

        private var weatherData: List<Weather> = listOf()

        fun setWeather(data: List<Weather>) {
            weatherData = data
            notifyDataSetChanged()
        }

    fun removeListener() {
        onItemViewClickListener = null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_main_recycler_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(weather: Weather) {
            itemView.apply {
                findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text =
                    weather.city.city
                setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(weather)
                }
            }
        }
    }
}


/*     override fun onCreateViewHolder(
         parent: ViewGroup,
         viewType: Int
     ): MainViewHolder {
         return MainViewHolder(
             LayoutInflater.from(parent.context)
                 .inflate(R.layout.fragment_main_recycler_item, parent, false) as View
         )
     }

     override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
         holder.bind(weatherData[position])
     }

     override fun getItemCount(): Int {
         return weatherData.size
     }

     inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

         fun bind(weather: Weather) {
             itemView.findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text = weather.city.city
             itemView.setOnClickListener {
                 Toast.makeText(
                     itemView.context,
                     weather.city.city,
                     Toast.LENGTH_LONG
                 ).show()
             }
         }
     }
 }*/
