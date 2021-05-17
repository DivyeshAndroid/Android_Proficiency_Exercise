package com.divyesh.exercise.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.divyesh.exercise.database.NationDetialDatabase.Companion.NationDetailTable

/**
 * [NationDetailEntity] :
 * class will be use to map SQLite table in database
 * mapping feed title ,description ,imageURl and main title for Table
 * @author Divyesh Kalotra
 * @version 1.0.0
 * @since 17-05-2021
 */


@Entity(tableName = NationDetailTable)
class NationDetailEntity(


    @ColumnInfo(name = "feed_title")
    var title: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "imageHref")
    var imageHref: String? = null,

    @ColumnInfo(name = "mainTitle")
    var mainTitle: String? = null
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "title")
    var id: Int = 0


    override fun toString(): String {
        return "MainTitle: $mainTitle , tite $title image URL: $imageHref "
    }
}
