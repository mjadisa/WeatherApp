package com.mujeeb.weatherapp.repo.city_detail


import com.mujeeb.weatherapp.data.city_detail.Response
import io.reactivex.Maybe

interface CityDetailDataSource {
    fun getWeather(city: Int): Maybe<Response>


}