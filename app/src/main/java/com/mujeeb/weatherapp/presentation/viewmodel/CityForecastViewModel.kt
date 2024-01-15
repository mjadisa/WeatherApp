package com.mujeeb.weatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujeeb.weatherapp.utils.DataHandler
import com.mujeeb.weatherapp.domain.usecases.AddForecastUseCase
import com.mujeeb.weatherapp.domain.usecases.GetRemoteForecastUseCase
import com.mujeeb.weatherapp.presentation.mapper.ForecastViewStateMapper
import com.mujeeb.weatherapp.presentation.viewstate.ForecastViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CityForecastViewModel @Inject constructor(
    private val getRemoteForecastUseCase: GetRemoteForecastUseCase,
    private val addForecastUseCase: AddForecastUseCase,
    private val mapper: ForecastViewStateMapper
) : ViewModel() {

    private val _result = MutableStateFlow<DataHandler<ForecastViewState>>(DataHandler.SUCCESS(
        ForecastViewState()
    ))
    val result: Flow<DataHandler<ForecastViewState>> = _result

    fun getWeatherData(city: Int) {
        viewModelScope.launch {
            _result.value = DataHandler.LOADING()
            getRemoteForecastUseCase(city).data?.let {
                _result.value =  DataHandler.SUCCESS(mapper.mapDomainForecastToViewState(it))
                addForecastUseCase(it)
            }
        }
    }


}
