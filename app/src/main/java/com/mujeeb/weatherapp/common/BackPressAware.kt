package com.mujeeb.weatherapp.common

/**
 * For anything (but primarily fragments) that might
 * need to handle back presses
 */
interface BackPressAware {
    fun onBackPressed(): Boolean = false
}