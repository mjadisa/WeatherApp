package com.mujeeb.weatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujeeb.weatherapp.domain.model.CityListResponse
import com.mujeeb.weatherapp.domain.usecases.AddCityListUseCase
import com.mujeeb.weatherapp.domain.usecases.GetRemoteCityListUseCase
import com.mujeeb.weatherapp.presentation.mapper.ForecastViewStateMapper
import com.mujeeb.weatherapp.presentation.viewstate.CityListResponseViewState
import com.mujeeb.weatherapp.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRemoteCityListUseCase: GetRemoteCityListUseCase,
    private val addCityListUseCase: AddCityListUseCase,
    private val mapper: ForecastViewStateMapper
) : ViewModel() {

    private val _result = MutableStateFlow<DataHandler<CityListResponseViewState>>(DataHandler.SUCCESS(CityListResponseViewState()))


    private val _searchQuery = MutableStateFlow<String>("")
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val result: Flow<DataHandler<CityListResponseViewState>> = _searchQuery
        .debounce(SEARCH_DELAY_MILLIS)
        .mapLatest {
            if (it.length > MIN_QUERY_LENGTH) {
                val response : CityListResponse = getRemoteCityListUseCase(it).data?.let { cityResponse ->
                    addCityListUseCase(cityResponse)
                    cityResponse
                } ?: CityListResponse()
                DataHandler.SUCCESS(mapper.mapDomainCityListResponseToViewState(response))
            } else {
                DataHandler.SUCCESS(CityListResponseViewState())
            }

        }
        .catch {
            // Log Error
        }




    fun searchCity(queryString: String) {
        viewModelScope.launch {
            _searchQuery.value = queryString
        }
    }


    companion object {
        private const val SEARCH_DELAY_MILLIS = 500L
        private const val MIN_QUERY_LENGTH = 2
    }

}

