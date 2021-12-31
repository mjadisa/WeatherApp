package com.mujeeb.weatherapp.repo.city_detail


import com.mujeeb.weatherapp.data.city_detail.Response
import io.reactivex.Maybe
import javax.inject.Inject

class CityDetailRepository @Inject constructor(private val remoteDataSource: CityDetailRemoteDataSource):
    CityDetailDataSource {
    override fun getWeather(city: Int): Maybe<Response> {
        return remoteDataSource.getWeather(city)

    }

}