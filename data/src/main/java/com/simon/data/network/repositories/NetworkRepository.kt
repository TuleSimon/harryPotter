package com.simon.data.network.repositories

import com.simon.data.models.characters.CharactersResponseItem
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {

    fun getCharacters(): Flow<NetworkResult<List<CharactersResponseItem>>>
}


sealed class NetworkResult<out T>() {

    data class Success<out T>(val data: T) : NetworkResult<T>()
    object Idle:NetworkResult<Nothing>()
    data class Failure<out T>(val exception: String) : NetworkResult<T>()
    object Loading : NetworkResult<Nothing>()
}