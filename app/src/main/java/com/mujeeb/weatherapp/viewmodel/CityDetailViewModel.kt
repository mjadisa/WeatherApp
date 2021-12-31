package com.mujeeb.weatherapp.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mujeeb.weatherapp.common.NetworkUtils
import com.mujeeb.weatherapp.data.city_detail.Response
import com.mujeeb.weatherapp.data.city_list.CityResponse
import com.mujeeb.weatherapp.repo.city_detail.CityDetailRepository
import com.mujeeb.weatherapp.ui.CityDetailRequestState
import com.mujeeb.weatherapp.ui.CityListRequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
@HiltViewModel
class CityDetailViewModel @Inject constructor(private val cityDetailRepository: CityDetailRepository, networkUtils: NetworkUtils) : BaseViewModel(networkUtils) {

    private val weatherObservable = MutableLiveData<CityDetailRequestState>()


    fun getWeatherObservable(): LiveData<CityDetailRequestState> = weatherObservable


    fun getWeatherData(city: Int) {
        if (weatherObservable.value == null) {
            compositeDisposable.add(cityDetailRepository.getWeather(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progressBarObservable.set(true) }
                .doOnEvent {
                        _ , _ -> progressBarObservable.set(false)
                        handleSubscription()
                }
                .subscribe({ handleSuccess(it) }, { handleError(it) }))
        }

    }





    private fun handleSuccess(response: Response) {

        weatherObservable.value = CityDetailRequestState.Success(response)
    }


    private fun handleError(throwable: Throwable?) {

        weatherObservable.value = CityDetailRequestState.Error(throwable?.message)

    }



    private fun handleSubscription() {
        if (!utils.isOnline()) {
            weatherObservable.value = CityDetailRequestState.Error( "Network Connection and Cached Data Not Available, " +
                    "Please try again when you have active network connection")
        }
    }

}
