package com.app.weatherapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.app.weatherapp.R
import com.app.weatherapp.model.ForecastTemperatureData
import com.app.weatherapp.util.Constant
import kotlinx.android.synthetic.main.item_forecast.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ForecastAdapter : RecyclerView.Adapter<ForecastViewHolder>() {

    private val items = ArrayList<ForecastTemperatureData.Temperature>()
    private lateinit var item: ForecastTemperatureData.Temperature

    fun setItems(items: ArrayList<ForecastTemperatureData.Temperature>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ForecastViewHolder(view)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        item = items[position]
        val millis = items[position].dt?.toLong() ?: 0L
        val timestamp: Long = millis * 1000L
        holder.tvDay.text = getDate(timestamp)
        holder.tvTemp.text =
            holder.tvTemp.context.getString(R.string.d_celsius, item.main?.temp?.toInt())
    }

    private fun getDate(timeStamp: Long): String? {
        return try {
            val sdf = SimpleDateFormat(Constant.DATE_FORMAT, Locale.getDefault())
            val netDate = Date(timeStamp)
            sdf.format(netDate)
        } catch (ex: Exception) {
            ""
        }
    }

}

class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvDay: AppCompatTextView = itemView.tvDay
    val tvTemp: AppCompatTextView = itemView.tvTemp
}

