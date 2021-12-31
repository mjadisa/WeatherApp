package com.mujeeb.weatherapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mujeeb.weatherapp.common.NetworkUtils
import com.mujeeb.weatherapp.data.city_list.CityResponse
import com.mujeeb.weatherapp.repo.city_list.CityListRepository
import com.mujeeb.weatherapp.ui.CityListRequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
@HiltViewModel
class CityListViewModel @Inject constructor(private val cityListRepository: CityListRepository,  networkUtils: NetworkUtils) : BaseViewModel(networkUtils)  {

    private val cityObservable = MutableLiveData<CityListRequestState>()


    fun getCityObservable(): LiveData<CityListRequestState> = cityObservable



    fun searchCity(city: String) {
        if (cityObservable.value == null) {
            compositeDisposable.add(cityListRepository.getCityList(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progressBarObservable.set(true) }
                .doOnEvent {
                        _, _ -> progressBarObservable.set(false)
                    handleSubscription()
                }
                .subscribe({ handleSuccess(it) }, { handleError(it) }))
        }

    }



    private fun handleSuccess(response: CityResponse) {

        cityObservable.value = CityListRequestState.LocationFoundSuccess(response)
    }


    private fun handleError(throwable: Throwable?) {

        cityObservable.value = CityListRequestState.SomethingWentWrongError(throwable?.message)
        
    }



    private fun handleSubscription() {
        if (!utils.isOnline()) {
            cityObservable.value = CityListRequestState.NoNetworkConnectionError( "Network Connection and Cached Data Not Available, " +
                    "Please try again when you have active network connection")
        }
    }



}

