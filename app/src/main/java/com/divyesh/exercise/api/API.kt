package com.divyesh.exercise.api

import com.divyesh.exercise.models.NationDetail
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Callback

class API : KoinComponent {

    private val apiClient:ApiClient by inject<ApiClient>()
    private val netWorkInterface: ApiInterface by lazy { apiClient.getNetworkInterface() }

    fun requestNationDescription(callBack: Callback<NationDetail>) {
        val requestCall = netWorkInterface.getData()
        requestCall.enqueue(callBack)
    }

}