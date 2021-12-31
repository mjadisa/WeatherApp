package com.mujeeb.weatherapp.repo.city_list


import com.mujeeb.weatherapp.BuildConfig.API_KEY
import com.mujeeb.weatherapp.data.city_list.CityResponse
import com.mujeeb.weatherapp.net.CityListApiCall
import io.reactivex.Maybe
import javax.inject.Inject

class CityListRemoteDataSource  @Inject constructor(private val cityApiCall: CityListApiCall) :
    CityListDataSource {
    override fun getCityList(city: String): Maybe<CityResponse> {
        return cityApiCall.getCityList(city, API_KEY)
            .flatMapMaybe { Maybe.just(it) }
    }

}

