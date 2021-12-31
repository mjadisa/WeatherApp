package com.mujeeb.weatherapp.repo.city_list


import com.mujeeb.weatherapp.data.city_list.CityResponse
import io.reactivex.Maybe
import javax.inject.Inject

class CityListRepository @Inject constructor(private val remoteDataSource: CityListRemoteDataSource):
    CityListDataSource {
    override fun getCityList(city: String): Maybe<CityResponse> {
        return remoteDataSource.getCityList(city)

    }

}
