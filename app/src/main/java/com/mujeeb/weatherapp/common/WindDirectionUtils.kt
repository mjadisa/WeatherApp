package com.mujeeb.weatherapp.common

class WindDirectionUtils {

    fun estimateDirection(degree : Double): Direction {
       return when(degree.toInt()){
            in 0..44  ->  Direction.North
            in 45..134 -> Direction.East
            in 134..224 -> Direction.South
            in 225..314 -> Direction.West
            else -> Direction.North
        }


    }
}