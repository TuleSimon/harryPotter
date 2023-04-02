package com.simon.data.di

import androidx.room.Room
import com.google.gson.Gson
import com.simon.data.datastore_storage.DataStoreUtils
import com.simon.data.datastore_storage.dataStore
import com.simon.data.local.database.CharactersDatabase
import com.simon.data.local.database.typeConverters.CharacterTypeConverter
import com.simon.data.local.database.typeConverters.GsonParser
import com.simon.data.network.ktor.ktorHttpClient
import com.simon.data.network.repositories.NetworkRepository
import com.simon.data.network.repositories.NetworkRepositoryImpl
import io.ktor.client.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * this refers to all online logic, like ktor, repository etc
 */
val NetworkModule = module{
    factory  <HttpClient> {
      ktorHttpClient(get())
    }

    single<NetworkRepository>{
        NetworkRepositoryImpl(get())
    }


}

/**
 * this refers to all offline logic, like datastore, room etc
 */
val LocalModule = module{
    single {
        DataStoreUtils(androidContext().dataStore)
    }
    single {
        Room.databaseBuilder(androidContext(), CharactersDatabase::class.java, "simon_db")
            .addTypeConverter(CharacterTypeConverter(GsonParser(Gson()))).build()
    }

    single{
       get<CharactersDatabase>().dao
    }


}