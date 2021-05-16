package com.app.weatherapp.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.weatherapp.R
import com.app.weatherapp.model.CurrentTemperatureData
import com.app.weatherapp.model.ForecastTemperatureData
import com.app.weatherapp.util.DataState
import com.app.weatherapp.util.DebouncedOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val CLICK_DURATION = 500L
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: ForecastAdapter

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        setupRecyclerView()
        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.CurrentTemperature)
        viewModel.setStateEvent(MainStateEvent.ForecastTemperature)

        btnRetry.setOnClickListener(object : DebouncedOnClickListener(CLICK_DURATION) {
            override fun onDebouncedClick(v: View?) {
                groupFailure?.visibility = View.GONE
                clMainContainer?.setBackgroundColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.bg_normal
                    )
                )
                viewModel.setStateEvent(MainStateEvent.CurrentTemperature)
            }
        })
    }

    private fun setupRecyclerView() {
        // Set the adapter
        adapter = ForecastAdapter()
        rvForecastTemp?.layoutManager = LinearLayoutManager(this)
        rvForecastTemp?.adapter = adapter
    }

    private fun populateRecyclerView(data: List<ForecastTemperatureData.Temperature?>?) {
        if (!data.isNullOrEmpty()) {
            rvForecastTemp?.visibility = View.VISIBLE
            adapter.setItems(ArrayList(data))
            val animMoveToTop = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top)
            rvForecastTemp?.startAnimation(animMoveToTop)
        } else {
            rvForecastTemp?.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun subscribeObservers() {
        viewModel.currentTempDataState.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success<CurrentTemperatureData> -> {
                    displayLoading(false)
                    setAppropriateView(true)
                    val weatherData = dataState.data
                    tvTemperature?.text =
                        getString(R.string.d_celsius, weatherData.main?.temp?.toInt())
                    tvCity?.text = weatherData.name
                }
                is DataState.Loading -> {
                    displayLoading(true)
                }
                is DataState.Error -> {
                    displayLoading(false)
                    setAppropriateView(false)
                }
            }
        })

        viewModel.forecastTempDataState.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success<ForecastTemperatureData> -> {
                    val weatherForecastData = dataState.data
                    populateRecyclerView(weatherForecastData.list)
                }
                is DataState.Loading -> {

                }
                is DataState.Error -> {

                }
            }
        })
    }

    private fun setAppropriateView(isSuccess: Boolean) {
        groupSuccess?.visibility = if (isSuccess) View.VISIBLE else View.GONE
        groupFailure?.visibility = if (!isSuccess) View.VISIBLE else View.GONE
        if (isSuccess) {
            clMainContainer?.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.bg_normal
                )
            )
        } else {
            clMainContainer?.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.bg_failure
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        rvForecastTemp?.clearAnimation()
        ivLoading?.clearAnimation()
    }

    private fun displayLoading(isLoading: Boolean) {
        val aniRotate = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
        if (isLoading) {
            ivLoading?.visibility = View.VISIBLE
            ivLoading?.startAnimation(aniRotate)
        } else {
            ivLoading?.visibility = View.GONE
            ivLoading?.clearAnimation()
        }
    }

}