package com.divyesh.exercise.util

/**
 * [AppState] :
 *
 * this sealed class use for decide different states  repository.
 *
 * @author Divyesh Kalotra
 * @version 1.0.0
 * @since 17-05-2021
 */

sealed class AppState<out R> {
    /**
     * data class for state receive data successfully.
     *
     * @param T for type of data to Return
     * @property data for passing receive data
     */
    data class Success<out T>(val data: T,val pageno:Int) : AppState<T>()

    /**
     * data class for state when no internet connection founds and retrive data from data base.
     *
     * @param T for type of data to Return
     * @property data for pass data retrive from database
     */
    data class NoConnection<out T>(val data: T,val pageno:Int) : AppState<T>()

    /**
     *data class for state error when error occurs during Api Call.
     *
     * @property exception for passing exception from network call.
     */
    data class Error(val exception: Exception) : AppState<Nothing>()

    /**
     *  object that state when recieves empty data from network.
     */
    object EmptyData : AppState<Nothing>()
    /**
     *  object that states when to show Loading.
     */
    object Loading : AppState<Nothing>()
}