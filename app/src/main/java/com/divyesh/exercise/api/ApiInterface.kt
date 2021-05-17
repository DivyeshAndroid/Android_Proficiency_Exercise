package com.divyesh.exercise.api

import com.divyesh.exercise.models.NationDetail
import retrofit2.http.GET

/**
 * [ApiInterface] :
 *
 * it Describes Api Endpoints for Restful API .
 *
 * @author Divyesh Kalotra
 * @version 1.0.0
 * @since 17-05-2021
 */

interface ApiInterface {

    @GET("s/2iodh4vg0eortkl/facts.json")
    suspend  fun getData() : NationDetail
}