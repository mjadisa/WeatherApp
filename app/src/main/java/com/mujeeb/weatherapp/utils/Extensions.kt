package com.mujeeb.weatherapp.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mujeeb.weatherapp.R
import com.mujeeb.weatherapp.data.enums.Direction
import com.mujeeb.weatherapp.data.enums.ErrorModel
import com.mujeeb.weatherapp.data.enums.ErrorType
import com.mujeeb.weatherapp.data.network.model.ErrorMessage
import com.mujeeb.weatherapp.data.network.model.ErrorResponse
import retrofit2.Response
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

fun Response<*>.mapErrorResponse(): ErrorResponse {
    val error: Type = object : TypeToken<ErrorResponse>() {}.type
    return try {
        Gson().fromJson(errorBody()?.string(), error)
    } catch (e: Exception) {
        ErrorResponse(ErrorMessage(EMPTY_STRING))
    }
}

fun ErrorResponse.mapErrorModel(): ErrorModel {
    return ErrorModel(ErrorType.UNKNOWN, this.error.message)
}

fun Int.toTimeStampFromDate(): String {
    val dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)
    return dateFormat.format(Date(toLong() * 1000))
}


fun Double.toEstimatedDirection(): Direction {
    return when (toInt()) {
        in 0..44 -> Direction.North
        in 45..134 -> Direction.East
        in 134..224 -> Direction.South
        in 225..314 -> Direction.West
        else -> Direction.North
    }
}


fun Direction.toDirectionImageResource(): Int {
    return when(this) {
        Direction.North -> R.drawable.icons8_north_direction_48
        Direction.East -> R.drawable.icons8_east_direction_48
        Direction.South -> R.drawable.icons8_south_direction_48
        else -> R.drawable.icons8_west_direction_48
    }
}