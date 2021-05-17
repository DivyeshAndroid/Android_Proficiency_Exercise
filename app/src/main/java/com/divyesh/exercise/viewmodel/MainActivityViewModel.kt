package com.divyesh.exercise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divyesh.exercise.models.NationDetail
import com.divyesh.exercise.repositories.NationDetailedRepostitory
import com.divyesh.exercise.util.AppLog
import com.divyesh.exercise.util.AppState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * [MainActivityViewModel]
 *
 * Retrieves data from Repository and provide it to UI .
 *
 *
 * @author Divyesh Kalotra
 * @version 1.0.0
 * @since 17-05-2021
 */


@KoinApiExtension
class MainActivityViewModel : ViewModel(), KoinComponent {

    private val tag: String = this::class.java.simpleName
    private val mDataState: MutableLiveData<AppState<NationDetail>> = MutableLiveData()
    val mCurrentState: LiveData<AppState<NationDetail>> get() = mDataState
    private val repostitory: NationDetailedRepostitory by inject()
    var currentPage = 1
    val noOFItemPerPage = 10
    var isLastPageVisited = false
    var isLastPageInt = -1

    /**
     *Function request for next page data .
     * */
    fun requestNextPage() {
        if (isLastPageInt == -1) {
            currentPage++
            if (!isLastPageVisited) {
                getNationDetails()
            }
            AppLog.e(tag, "Request Page $currentPage")
        }

    }

    /**
     * this function retrives data from repository with asynchronously with help of Coroutine.
     * it retrieves [Flow] of [AppState] and set value in [LiveData]
     */
    fun getNationDetails() {
        viewModelScope.launch {
            repostitory.getNationDetials(page = currentPage, noPageItems = noOFItemPerPage)
                .onEach {
                    mDataState.value = it
                }.launchIn(viewModelScope)
        }

    }

    /**
     *Reset flags for pagination.
     *
     */

    fun resetPageData() {
        currentPage = 1
        isLastPageVisited = false
        isLastPageInt = -1
    }
}
