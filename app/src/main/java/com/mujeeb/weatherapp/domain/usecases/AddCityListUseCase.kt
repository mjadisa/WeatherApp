package com.mujeeb.weatherapp.domain.usecases

import com.mujeeb.weatherapp.domain.model.CityListResponse
import com.mujeeb.weatherapp.domain.repository.CityListRepository
import javax.inject.Inject

class AddCityListUseCase @Inject constructor(private val repo: CityListRepository) {
    suspend operator fun invoke(cityListResponse: CityListResponse) {
        return repo.insertCityListResponse(cityListResponse = cityListResponse)
    }
}
