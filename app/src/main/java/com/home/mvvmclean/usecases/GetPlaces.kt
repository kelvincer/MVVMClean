package com.home.mvvmclean.usecases

import androidx.lifecycle.MutableLiveData
import com.home.mvvmclean.data.DataModel
import com.home.mvvmclean.response.PlaceApiResponse
import javax.inject.Inject

class GetPlaces @Inject constructor(val datamodel: DataModel){

    val responseData = MutableLiveData<PlaceApiResponse>()
    val errorMessage = MutableLiveData<String> ()

    init {
        datamodel.responseData.observeForever {
            responseData.value = it
        }
        datamodel.errorMessage.observeForever{
            errorMessage.value = it
        }
    }

    fun execute(query: String){
        datamodel.execute(query)
    }
}