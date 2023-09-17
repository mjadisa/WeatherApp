package com.mujeeb.weatherapp.di.module

import com.mujeeb.weatherapp.data.network.mapper.ApiMapper
import com.mujeeb.weatherapp.data.network.mapper.ApiMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ApiMapperModule {
    @Binds
    abstract fun bindApiMapper(mapperImp: ApiMapperImpl) : ApiMapper
}

