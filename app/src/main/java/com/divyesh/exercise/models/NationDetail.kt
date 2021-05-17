package com.divyesh.exercise.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

/**
 * [NationDetail] :
 *
 * Class hold value for [Row] and title  parse from rest API response.
 *
 * @author Divyesh Kalotra
 * @version 1.0.0
 * @since 17-05-2021
 */

class NationDetail {
    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("rows")
    @Expose
    var rows: ArrayList<Row>? = null

}

