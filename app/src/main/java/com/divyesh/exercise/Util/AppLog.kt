package com.divyesh.exercise.Util

import android.util.Log
import com.divyesh.exercise.BuildConfig

object AppLog {

    fun e(TAG:String,msg:String)
    {
        if(BuildConfig.DEBUG)
        {
            Log.e(TAG,msg)
        }
    }
    fun e(TAG:String,msg:String,t: Throwable)
    {
        if(BuildConfig.DEBUG)
        {
            Log.e(TAG,msg,t)
        }
    }
}