package com.mujeeb.weatherapp.repo.city_detail


import com.mujeeb.weatherapp.BuildConfig.API_KEY
import com.mujeeb.weatherapp.common.UNITS
import com.mujeeb.weatherapp.data.city_detail.Response
import com.mujeeb.weatherapp.net.CityDetailApiCall
import io.reactivex.Maybe
import javax.inject.Inject

class CityDetailRemoteDataSource  @Inject constructor(private val weatherApiCall: CityDetailApiCall) :
    CityDetailDataSource {
    override fun getWeather(city: Int): Maybe<Response> {
        return weatherApiCall.getWeather(city,UNITS, API_KEY)
            .flatMapMaybe { Maybe.just(it) }
    }

}



