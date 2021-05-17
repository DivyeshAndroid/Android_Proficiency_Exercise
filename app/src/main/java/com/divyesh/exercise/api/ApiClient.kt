package com.divyesh.exercise.api

import com.divyesh.exercise.util.Constant
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * [ApiClient] :
 * provides client for Retrofit and provide instance of [ApiInterface] for calling endpoints of Restful API.
 *
 * @author Divyesh Kalotra
 * @version 1.0.0
 * @since 17-05-2021
 */


@KoinApiExtension
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