package com.divyesh.exercise.api

import com.divyesh.exercise.models.NationDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET

interface ApiInterface {

    @GET("s/2iodh4vg0eortkl/facts.json")
    fun getData() : Call<NationDetail>
}