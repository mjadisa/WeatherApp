package com.mujeeb.weatherapp.presentation.navigation


interface Destination {
    val route: String
}

object Main : Destination {
    override val route = "main"
}

object Details : Destination {
    override val route = "details"
}

