package com.mujeeb.weatherapp.ui

import com.mujeeb.weatherapp.data.city_list.Result


interface SelectCityInterface {

    fun onItemSelected(cityList: Result)
}
