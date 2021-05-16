package com.app.weatherapp.ui

import androidx.lifecycle.*
import com.app.weatherapp.model.CurrentTemperatureData
import com.app.weatherapp.model.ForecastTemperatureData
import com.app.weatherapp.repository.MainRepository
import com.app.weatherapp.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val mainRepository: MainRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _currentTempDataState: MutableLiveData<DataState<CurrentTemperatureData>> =
        MutableLiveData()
    private val _forecastTempDataState: MutableLiveData<DataState<ForecastTemperatureData>> =
        MutableLiveData()

    val currentTempDataState: LiveData<DataState<CurrentTemperatureData>>
        get() = _currentTempDataState

    val forecastTempDataState: LiveData<DataState<ForecastTemperatureData>>
        get() = _forecastTempDataState

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.CurrentTemperature -> {
                    mainRepository.getCurrentTemperature()
                        .onEach { dataState ->
                            _currentTempDataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is MainStateEvent.ForecastTemperature -> {
                    mainRepository.getForecastTemperature()
                        .onEach { dataState ->
                            _forecastTempDataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is MainStateEvent.None -> {
                    // No action
                }
            }
        }
    }
}


sealed class MainStateEvent {
    object CurrentTemperature : MainStateEvent()
    object ForecastTemperature : MainStateEvent()
    object None : MainStateEvent()
}