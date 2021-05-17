package com.divyesh.exercise.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.divyesh.exercise.api.ApiClient
import com.divyesh.exercise.database.NationDetialDatabase
import com.divyesh.exercise.repositories.NationDetailedRepostitory
import com.divyesh.exercise.util.Constant
import com.divyesh.exercise.viewmodel.MainActivityViewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * [AppModule] :
 *
 * 
 *
 * @author Divyesh Kalotra
 * @version 1.0.0
 * @since 17-05-2021
 */

@KoinApiExtension
class AppModule(private val mContext: Context) {

    val appModule: Module = module {

        single { ApiClient().getNetworkInterface() }

        single { Constant() }

        factory {
            Room.databaseBuilder(
                mContext,
                NationDetialDatabase::class.java,
                NationDetialDatabase.DataBaseName
            ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
                .build()
        }

        factory {
            val nationDetailsDatabase: NationDetialDatabase = get()
            nationDetailsDatabase.getNationDetailsDao()
        }

        single {
            NationDetailedRepostitory(get(), get())
        }



    }
}