package com.divyesh.exercise.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class NationDetail {
    @SerializedName("title")
    @Expose
    val title: String? = null

    @SerializedName("rows")
    @Expose
    val rows: List<Row>? = null

}

