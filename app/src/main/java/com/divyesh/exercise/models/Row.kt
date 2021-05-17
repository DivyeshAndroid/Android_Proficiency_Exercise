package com.divyesh.exercise.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

/**
 * [Row] :
 * Class parse and hold json values from restful Api response value.
 *
 * @author Divyesh Kalotra
 * @version 1.0.0
 * @since 17-05-2021
 */

class Row constructor(


    @PrimaryKey(autoGenerate = false)
    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    var title: String? = null,

    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description")
    var description: String? = null,

    @SerializedName("imageHref")
    @Expose
    @ColumnInfo(name = "imageHref")
    var imageHref: String? = null
)