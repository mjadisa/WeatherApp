package com.mujeeb.weatherapp.data.city_detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Sys {

    @SerializedName("pod")
    @Expose
    var pod: String? = null

}