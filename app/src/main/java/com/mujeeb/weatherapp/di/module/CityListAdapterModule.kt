package com.mujeeb.weatherapp.di.module

import android.content.Context
import androidx.fragment.app.Fragment
import com.mujeeb.weatherapp.ui.CityListFragment
import com.mujeeb.weatherapp.ui.CityListRecyclerViewAdapter
import com.mujeeb.weatherapp.ui.SelectCityInterface
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(FragmentComponent::class)
@Module
object CityListAdapterModule {

    @Provides
    fun provideCityListRecyclerViewAdapter(selectCityInterface: SelectCityInterface, @ApplicationContext context: Context, picasso: Picasso): CityListRecyclerViewAdapter {
        return CityListRecyclerViewAdapter(selectCityInterface, context, picasso)
    }


    @Provides
    fun provideSelectCityInterface(fragment: Fragment): SelectCityInterface {
        return fragment as SelectCityInterface
    }
}