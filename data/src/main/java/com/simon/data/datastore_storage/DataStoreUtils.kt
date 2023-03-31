package com.simon.data.datastore_storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class DataStoreUtils(private val prefsDataStore: DataStore<Preferences>) {

}

object PreferenceKeys {

//    var userTokenPrefKey = stringPreferencesKey("")
}