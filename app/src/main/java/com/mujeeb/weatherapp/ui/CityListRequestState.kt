package com.mujeeb.weatherapp.ui

import com.mujeeb.weatherapp.data.city_list.CityResponse

sealed class CityListRequestState{
    class LocationFoundSuccess(val cityResponse : CityResponse) : CityListRequestState()
    class SomethingWentWrongError(val error: String?): CityListRequestState()
    class NoNetworkConnectionError(val error: String?): CityListRequestState()
}