package com.mujeeb.weatherapp.viewmodel


import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mujeeb.weatherapp.common.NetworkUtils
import io.reactivex.disposables.CompositeDisposable


abstract  class BaseViewModel(protected val utils: NetworkUtils) : ViewModel() {


    val compositeDisposable = CompositeDisposable()

    val progressBarObservable = ObservableBoolean(false)



    fun getProgressObservable() = progressBarObservable



    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}