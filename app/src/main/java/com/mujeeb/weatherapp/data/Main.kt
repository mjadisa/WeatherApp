package com.mujeeb.weatherapp.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Main {

    @SerializedName("temp")
    @Expose
    var temp: Float? = null
    @SerializedName("temp_min")
    @Expose
    var tempMin: Float? = null
    @SerializedName("temp_max")
    @Expose
    var tempMax: Float? = null
    @SerializedName("pressure")
    @Expose
    var pressure: Float? = null
    @SerializedName("sea_level")
    @Expose
    var seaLevel: Float? = null
    @SerializedName("grnd_level")
    @Expose
    var grndLevel: Float? = null
    @SerializedName("humidity")
    @Expose
    var humidity: Int? = null
    @SerializedName("temp_kf")
    @Expose
    var tempKf: Float? = null

}