package com.mujeeb.weatherapp.di.module

import android.content.Context
import com.mujeeb.weatherapp.BuildConfig
import com.mujeeb.weatherapp.common.REQUEST_TIMEOUT
import com.mujeeb.weatherapp.common.RETROFIT_CACHE_SIZE
import com.mujeeb.weatherapp.net.CityListApiCall
import com.mujeeb.weatherapp.net.CityDetailApiCall
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

        @Provides
        @Singleton
        fun provideRetrofitCache(@ApplicationContext context: Context) = Cache(
            context.cacheDir,
            RETROFIT_CACHE_SIZE
        )

        @Provides
        @Singleton
        fun provideLogger(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        @Provides
        @Singleton
        fun provideOkHttpClient(cache: Cache, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

    @Provides
    @Singleton
    fun provideWeatherApiCall(retrofit: Retrofit): CityDetailApiCall = retrofit.create(CityDetailApiCall::class.java)


    @Provides
    @Singleton
    fun provideCityApiCall(retrofit: Retrofit): CityListApiCall = retrofit.create(CityListApiCall::class.java)




}