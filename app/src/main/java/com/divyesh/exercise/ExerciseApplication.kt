package com.divyesh.exercise

import android.app.Application
import com.divyesh.exercise.di.AppModule
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin

/**
 * [ExerciseApplication] :
 *
 *  Application class , initialize Koin Dependency and provide instance of itself.
 *
 * @author Divyesh Kalotra
 * @version 1.0.0
 * @since 17-05-2021
 */

class ExerciseApplication : Application() {

    companion object {
        var instance: ExerciseApplication? = null
    }

    @KoinApiExtension
    override fun onCreate() {
        super.onCreate()
        startKoin { modules(AppModule(this@ExerciseApplication.applicationContext).appModule) }
        instance = this
    }


}
