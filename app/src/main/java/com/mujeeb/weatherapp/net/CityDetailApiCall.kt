package com.mujeeb.weatherapp.net


import com.mujeeb.weatherapp.BuildConfig
import com.mujeeb.weatherapp.data.city_detail.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CityDetailApiCall {

    @GET(BuildConfig.WEATHER_ENDPOINT)
    fun getWeather(
        @Query("id") city: Int,
        @Query("units") units: String,
        @Query("appid") apikey: String
    ): Single<Response>
}