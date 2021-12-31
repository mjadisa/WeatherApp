package com.mujeeb.weatherapp.net


import com.mujeeb.weatherapp.BuildConfig
import com.mujeeb.weatherapp.data.city_list.CityResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CityListApiCall {

    @GET(BuildConfig.CITY_LIST_ENDPOINT)
    fun getCityList(
        @Query("q") searchItem: String,
        @Query("appid") apikey: String
    ): Single<CityResponse>
}