package com.mujeeb.weatherapp.presentation.viewmodel

import MainCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mujeeb.weatherapp.domain.model.Forecast
import com.mujeeb.weatherapp.domain.usecases.AddForecastUseCase
import com.mujeeb.weatherapp.domain.usecases.GetRemoteForecastUseCase
import com.mujeeb.weatherapp.presentation.mapper.ForecastViewStateMapper
import com.mujeeb.weatherapp.presentation.viewstate.ForecastViewState
import com.mujeeb.weatherapp.utils.DataHandler
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ApiCityForecastViewModelTest {
    private val addForecastUseCase = mockk<AddForecastUseCase>()
    private val getRemoteForecastUseCase = mockk<GetRemoteForecastUseCase>()
    private val forecast = mockk<Forecast>()
    private val mapper = mockk<ForecastViewStateMapper>()
    private val forecastViewState = mockk<ForecastViewState>()

    private lateinit var viewModel: CityForecastViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        viewModel = CityForecastViewModel(getRemoteForecastUseCase,  addForecastUseCase, mapper)
    }

    @Test
    fun `WHEN city weather data is requested THEN city weather data is returned and cached`() = runTest {

        // WHEN
        coEvery { getRemoteForecastUseCase.invoke(CITY_ID) } returns DataHandler.SUCCESS(forecast)
        coEvery { addForecastUseCase.invoke(forecast)  } returns Unit
        coEvery {  mapper.mapDomainForecastToViewState(forecast)} returns forecastViewState
        viewModel.getWeatherData(CITY_ID)

        // THEN
        Assert.assertEquals(viewModel.result.first().data, forecastViewState)
    }


    companion object {
        private const val CITY_ID = 2355474 // id for London
    }
}
