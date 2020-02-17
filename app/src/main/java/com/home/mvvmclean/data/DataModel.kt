package com.home.mvvmclean.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.home.mvvmclean.api.PlacesApiService
import com.home.mvvmclean.response.PlaceApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class DataModel @Inject constructor(){

    val TAG = DataModel::class.java.simpleName
    val BASE_URL = "https://maps.googleapis.com/maps/api/place/"
    val GOOGLE_PLACE_API_KEY = "AIzaSyAMni--tCJGoCbW-RsdcfhWBDEEC0uOhDQ"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    val responseData = MutableLiveData<PlaceApiResponse>()
    val errorMessage = MutableLiveData<String> ()

    fun execute(query: String){

        val service = retrofit.create(PlacesApiService::class.java)

        val call: Call<PlaceApiResponse> = service.getResult(
            query,
            GOOGLE_PLACE_API_KEY
        )
        call.enqueue(object : Callback<PlaceApiResponse?> {
            override fun onResponse(call: Call<PlaceApiResponse?>,
                response: Response<PlaceApiResponse?>) {
                if (response.isSuccessful()) {
                    val placeApiResponse: PlaceApiResponse? = response.body()
                    if (placeApiResponse?.getStatus().equals("OK")) {
                        Log.d(TAG, "size: " + placeApiResponse?.results?.size)
                        responseData.value = placeApiResponse
                    } else {
                        //post(SearchEvent.ERROR, placeApiResponse.getStatus())
                        Log.d(TAG, "size: " + response.message())
                        errorMessage.value = placeApiResponse?.status
                    }
                } else {
                    //post(SearchEvent.ERROR, response.message())
                    Log.d(TAG, "size: " + response.message())
                    errorMessage.value = response.message()
                }
            }

            override fun onFailure(call: Call<PlaceApiResponse?>, t: Throwable) {
                t.printStackTrace()
                //post(SearchEvent.ERROR, t.localizedMessage)
                errorMessage.value = t.message
            }
        })
    }
}