package com.mujeeb.weatherapp.domain.usecases

import com.mujeeb.weatherapp.domain.model.CityListResponse
import com.mujeeb.weatherapp.domain.repository.CityListRepository
import com.mujeeb.weatherapp.utils.DataHandler
import javax.inject.Inject

class GetRemoteCityListUseCase @Inject constructor(private val repo: CityListRepository) {
    suspend operator fun invoke(city: String): DataHandler<CityListResponse> {
        return repo.getCityListResponse(city = city)
    }
}
