package com.divyesh.exercise.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * [NationDetialDatabase] :
 * it is database class that provide [NationDertailsDao] and
 *
 * @author Divyesh Kalotra
 * @version 1.0.0
 * @since 17-05-2021
 */

@Database(entities = [NationDetailEntity::class],version = 6)
abstract class NationDetialDatabase :RoomDatabase() {

    abstract  fun getNationDetailsDao() :NationDertailsDao

    companion object{
        val DataBaseName:String ="nation_details_db"
        const val NationDetailTable = "nation_detail_table"
    }
}