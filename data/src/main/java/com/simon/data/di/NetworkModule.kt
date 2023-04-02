package com.simon.data.di

import androidx.room.Room
import com.google.gson.Gson
import com.simon.data.data.datasource.CharactersDataSource
import com.simon.data.data.local.LocalRepository
import com.simon.data.data.local.database.CharactersDatabase
import com.simon.data.data.local.database.typeConverters.CharacterTypeConverter
import com.simon.data.data.local.database.typeConverters.GsonParser
import com.simon.data.data.network.ktor.ktorHttpClient
import com.simon.data.data.network.repositories.NetworkRepository
import com.simon.data.data.network.repositories.NetworkRepositoryImpl
import io.ktor.client.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * this refers to all online logic, like ktor, repository etc
 */
val NetworkModule = module{
    factory  {
      ktorHttpClient()
    }

    single {
        NetworkRepositoryImpl(get())
    }

    single{
        LocalRepository(get())
    }

    single<NetworkRepository>{
        CharactersDataSource()
    }


}

/**
 * this refers to all offline logic, like datastore, room etc
 */
val LocalModule = module{

    single {
        Room.databaseBuilder(androidContext(), CharactersDatabase::class.java, "simon_db")
            .addTypeConverter(CharacterTypeConverter(GsonParser(Gson()))).build()
    }

    single{
       get<CharactersDatabase>().dao
    }


}