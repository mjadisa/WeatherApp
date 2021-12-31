package com.mujeeb.weatherapp.ui

import com.mujeeb.weatherapp.data.city_detail.Response


sealed class CityDetailRequestState{
    class Success(val response : Response) : CityDetailRequestState()
    class Error(val error: String?): CityDetailRequestState()
}