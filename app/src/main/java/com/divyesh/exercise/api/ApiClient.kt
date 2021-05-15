package com.divyesh.exercise.api

import com.divyesh.exercise.Util.Constant
import com.google.gson.GsonBuilder
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient :KoinComponent {

    val constant :Constant by inject()
    private val retrofit = Retrofit.Builder()
        .baseUrl(constant.baseURL)
        //.client()
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getNetworkInterface()   : ApiInterface = retrofit.create(ApiInterface::class.java)
}