package com.divyesh.exercise.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


/**
 * [NationDertailsDao] :
 *
 * interface provides methods to perform query operations into Database.
 *
 * @author Divyesh Kalotra
 * @version 1.0.0
 * @since 17-05-2021
 */

@Dao
interface NationDertailsDao {
    /**
     * Insert Data into table
     *
     * @param detail Entity or row  to store in Database
     * @return
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(detail: NationDetailEntity): Long

    /**
     * retrieve data from Table according to pagination
     *
     * @param mOffset position to start taking Data from query
     * @param mNoOfItemsPerPage counts to take no of item per page
     * @return
     */
    @Query("Select * from ${NationDetialDatabase.NationDetailTable}  Limit :mNoOfItemsPerPage OFFset :mOffset ")
    fun getGetNationDetails(
        mOffset: Int,
        mNoOfItemsPerPage: Int
    ): List<NationDetailEntity>

    /**
     *Clear whole table for new updated data
     */
    @Query("Delete from ${NationDetialDatabase.NationDetailTable} ")
    fun clearData(
    )
}