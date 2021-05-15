package com.divyesh.exercise.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.divyesh.exercise.R
import com.divyesh.exercise.Util.AppLog
import com.divyesh.exercise.api.API
import com.divyesh.exercise.models.NationDetail
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val apiInstance: API by inject()
    private val TAG: String = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestDataFromNetwork()
    }

    /*Requesting Data From Network*/
    private fun requestDataFromNetwork() {
        apiInstance.requestNationDescription(object : Callback<NationDetail> {
            override fun onResponse(call: Call<NationDetail>, response: Response<NationDetail>) {
                if (response.isSuccessful) {
                    val responseDetail = response.body()
                    if (responseDetail != null) {
                        AppLog.e(TAG, responseDetail.title!!)
                    } else {
                        AppLog.e(TAG, getString(R.string.no_data_response))
                    }
                }
            }

            override fun onFailure(call: Call<NationDetail>, t: Throwable) {
                
                Toast.makeText(this@MainActivity,getString(R.string.network_request_error),Toast.LENGTH_SHORT)
                AppLog.e(TAG, getString(R.string.network_request_error), t)
            }
        })
    }
}