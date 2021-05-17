package com.divyesh.exercise.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.divyesh.exercise.BuildConfig


/**
 * [AppLog] :
 *object use for Logging content from one place.
 *
 * @author Divyesh Kalotra
 * @version 1.0.0
 * @since 17-05-2021
 */

object AppLog {

    fun e(tag:String,msg:String)
    {
        if(BuildConfig.DEBUG)
        {
            Log.e(tag,msg)
        }
    }

}

/**
 * [ConnectionUtil] :
 * Utility object for checking whether internet connection available or not .s
 *
 * @author Divyesh Kalotra
 * @version 1.0.0
 * @since 17-05-2021
 */

object ConnectionUtil
{
    /**
     * function check is internet connection is Available or not.
     *
     * @param context for Getting [ConnectivityManager] service.
     * @return [Boolean] is internet connection is available or not.
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }

}
