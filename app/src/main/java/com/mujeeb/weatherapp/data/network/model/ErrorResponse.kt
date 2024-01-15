package com.mujeeb.weatherapp.data.network.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error") val error: ErrorMessage
)
