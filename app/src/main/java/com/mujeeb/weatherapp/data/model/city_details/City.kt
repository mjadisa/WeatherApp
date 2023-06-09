package com.mujeeb.weatherapp.data.model.city_details

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mujeeb.weatherapp.data.model.Coord

class City {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("coord")
    @Expose
    var coord: Coord? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("timezone")
    @Expose
    var timezone: Int? = null
}
