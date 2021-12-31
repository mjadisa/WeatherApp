package com.mujeeb.weatherapp.repo.city_list


import com.mujeeb.weatherapp.data.city_list.CityResponse
import io.reactivex.Maybe

interface CityListDataSource {
    fun getCityList(city: String): Maybe<CityResponse>

}