package com.home.mvvmclean.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.home.mvvmclean.di.DaggerMainComponent
import com.home.mvvmclean.response.PlaceApiResponse
import com.home.mvvmclean.usecases.GetPlaces
import javax.inject.Inject

class MainViewModel : ViewModel() {

    val responseData = MutableLiveData<List<String>>()
    val errorMessage = MutableLiveData<String>()
    val progressValue = MutableLiveData<Int>()

    @Inject
    lateinit var getPlaceUseCase: GetPlaces

    init {
        DaggerMainComponent.create().injectDependencies(this)
        getPlaceUseCase.responseData.observeForever{
            responseData.value = getNames(it)
            progressValue.value = View.GONE
        }
        getPlaceUseCase.errorMessage.observeForever{
            errorMessage.value = it
            progressValue.value = View.GONE
        }
    }

    fun newSearch(query: String){
        getPlaceUseCase.execute(query)
        progressValue.value = View.VISIBLE
    }

    fun getNames(r: PlaceApiResponse): List<String>{
        val list = mutableListOf<String>()
        r.results.forEach {
            list.add(it.name)
        }
        return list
    }
}