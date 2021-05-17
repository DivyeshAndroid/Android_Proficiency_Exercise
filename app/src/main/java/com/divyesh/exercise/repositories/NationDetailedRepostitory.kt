package com.divyesh.exercise.repositories

import com.divyesh.exercise.ExerciseApplication
import com.divyesh.exercise.api.ApiInterface
import com.divyesh.exercise.database.NationDertailsDao
import com.divyesh.exercise.database.NationDetailEntity
import com.divyesh.exercise.models.NationDetail
import com.divyesh.exercise.models.Row
import com.divyesh.exercise.util.AppLog
import com.divyesh.exercise.util.AppState
import com.divyesh.exercise.util.ConnectionUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * [NationDetailedRepostitory] :
 *
 * Repository class that use to provide data to view model . also store and retrieve data from database.
 *
 * @author Divyesh Kalotra
 * @version 1.0.0
 * @since 17-05-2021
 */

class NationDetailedRepostitory constructor(
    private val mApi: ApiInterface,
    private val databasedao: NationDertailsDao
) {
    private val tag = this::class.java.simpleName

    suspend fun getNationDetials(page: Int, noPageItems: Int): Flow<AppState<NationDetail>> =
        flow {
            var currentState: AppState<NationDetail> = AppState.Loading
            emit(currentState)
            var response: NationDetail? = null

            if (!ConnectionUtil.isNetworkAvailable(ExerciseApplication.instance!!.applicationContext)) {
                currentState = AppState.NoConnection(
                    insertAndRetriveDataFromDataBase(
                        false,
                        null,
                        page,
                        noPageItems
                    ),page
                )
                emit(currentState)
                return@flow
            }
            try {
                response = mApi.getData()
            } catch (exception: Exception) {
                currentState = AppState.Error(exception = exception)
                emit(currentState)
                return@flow
            }
            currentState = AppState.Success(

                insertAndRetriveDataFromDataBase(
                    true,
                    response,
                    page,
                    noPageItems
                ),page
            )
            emit(currentState)
        }

    private fun insertAndRetriveDataFromDataBase(
        shouldInsert: Boolean,
        responseDetail: NationDetail?,
        page: Int,
        noPageItems: Int
    ): NationDetail {
        if (responseDetail != null && shouldInsert) {
            insertDataintoDataBase(responseDetail)
        }
        val offset = (page - 1) * noPageItems
        val nationDetailEntityList =
            databasedao.getGetNationDetails(
                offset,
                noPageItems
            )
        val mNationDetail = NationDetail()
        val mRowList = ArrayList<Row>()
        nationDetailEntityList.forEach {
            AppLog.e(tag, it.toString() + "Current Page : $page")
            mNationDetail.title = it.mainTitle
            mRowList.add(
                Row(
                    it.title,
                    it.description,
                    it.imageHref
                )
            )

        }
        mNationDetail.rows = mRowList
        return mNationDetail
    }


    private fun insertDataintoDataBase(responseDetail: NationDetail) {
        databasedao.clearData()
        for (datail in responseDetail.rows!!) {

            databasedao.insert(
                NationDetailEntity(
                    datail.title,
                    datail.description,
                    datail.imageHref,
                    responseDetail.title
                )
            )

        }

    }
}