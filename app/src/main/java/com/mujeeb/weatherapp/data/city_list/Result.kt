package com.mujeeb.weatherapp.data.city_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mujeeb.weatherapp.data.*

data class Result (

    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("coord")
    @Expose
    var coord: Coord? = null,
    @SerializedName("main")
    @Expose
    var main: Main? = null,
    @SerializedName("dt")
    @Expose
    var dt: Int? = null,
    @SerializedName("wind")
    @Expose
    var wind: Wind? = null,
    @SerializedName("sys")
    @Expose
    var sys: Sys? = null,
    @SerializedName("rain")
    @Expose
    var rain: Any? = null,
    @SerializedName("snow")
    @Expose
    var snow: Any? = null,
    @SerializedName("clouds")
    @Expose
    var clouds: Clouds? = null,
    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null

)