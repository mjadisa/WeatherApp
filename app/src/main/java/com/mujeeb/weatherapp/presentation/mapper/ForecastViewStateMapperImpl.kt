package com.mujeeb.weatherapp.presentation.mapper

import com.mujeeb.weatherapp.domain.model.City
import com.mujeeb.weatherapp.domain.model.CityListResponse
import com.mujeeb.weatherapp.presentation.viewstate.CityViewState
import com.mujeeb.weatherapp.domain.model.Forecast
import com.mujeeb.weatherapp.domain.model.ForecastResult
import com.mujeeb.weatherapp.domain.model.Weather
import com.mujeeb.weatherapp.domain.model.WeatherResult
import com.mujeeb.weatherapp.presentation.viewstate.CityListResponseViewState
import com.mujeeb.weatherapp.presentation.viewstate.Clouds
import com.mujeeb.weatherapp.presentation.viewstate.Coord
import com.mujeeb.weatherapp.presentation.viewstate.ForecastResultViewState
import com.mujeeb.weatherapp.presentation.viewstate.ForecastSys
import com.mujeeb.weatherapp.presentation.viewstate.ForecastViewState
import com.mujeeb.weatherapp.presentation.viewstate.Main
import com.mujeeb.weatherapp.presentation.viewstate.Rain
import com.mujeeb.weatherapp.presentation.viewstate.Sys
import com.mujeeb.weatherapp.presentation.viewstate.WeatherViewState
import com.mujeeb.weatherapp.presentation.viewstate.WeatherResultViewState
import com.mujeeb.weatherapp.presentation.viewstate.Wind
import javax.inject.Inject

class ForecastViewStateMapperImpl @Inject constructor() : ForecastViewStateMapper {
    override fun mapDomainCityListResponseToViewState(cityListResponse: CityListResponse): CityListResponseViewState {
        return with(cityListResponse) {
            CityListResponseViewState(
                list = list?.map {
                    mapDomainWeatherResultToViewState(it)
                }
            )
        }
    }

    private fun mapDomainWeatherResultToViewState(result: WeatherResult) = with(result) {
        WeatherResultViewState(
            id =  id,
            name =  name,
           coord = Coord(
                   lon =  coord?.lon,
                   lat =  coord?.lat
           ),
           main = Main(
                temp =  main?.temp
           ),
           dt =  dt,
           wind = Wind(
               speed =  wind?.speed,
               deg =  wind?.deg
           ),
           sys = Sys(
             country =  sys?.country
           ),
           rain = Rain(
              h = null
           ),
           snow = null,
           clouds = Clouds(
               all =  clouds?.all
           ),
           weather =   weather?.map {
               mapDomainWeatherToViewState(it)
           }

        )

    }

    private fun mapDomainForecastResultToViewState(result: ForecastResult) = with(result) {
        ForecastResultViewState(
            dt = dt,
            main = Main(
               temp = main?.temp
            ),
            weather = weather?.map { mapDomainWeatherToViewState(it) },
            clouds = Clouds(
                all = clouds?.all
            ),
            wind = Wind(
                speed = wind?.speed,
                deg = wind?.deg
            ),
            rain = Rain(
                h = rain?.h
            ),
            sys = ForecastSys(
                pod = sys?.pod
            ),
            dtTxt = dtTxt
        )
    }

    private fun mapDomainWeatherToViewState(weather: Weather) = with(weather) {
        WeatherViewState(
            id = id,
            main = main,
            description = description,
            icon= icon,
        )
    }
    private fun mapDomainCityToViewState(city: City) = with(city) {
        CityViewState(
            id = id,
            name = name,
            coord = Coord(
                lon = coord?.lon,
                lat = coord?.lat
            ),
            country = country,
            timezone = timezone

        )
    }

    override fun mapDomainForecastToViewState(forecast: Forecast): ForecastViewState {
        return with(forecast) {
            ForecastViewState(
                list = list?.map {
                    mapDomainForecastResultToViewState(it)
                },
                city = city?.let { mapDomainCityToViewState(it) }
            )
        }
    }


}



