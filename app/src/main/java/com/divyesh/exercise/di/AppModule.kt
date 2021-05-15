package com.divyesh.exercise.di

import com.divyesh.exercise.Util.Constant
import com.divyesh.exercise.api.API
import com.divyesh.exercise.api.ApiClient
import org.koin.core.module.Module
import org.koin.dsl.module

class AppModule {

    val appModule : Module = module {
        single { API() }
        single { ApiClient() }
        single { Constant() }
    }
}