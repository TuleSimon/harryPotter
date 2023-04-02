package com.simon.data.data.network.repositories

import com.simon.data.models.characters.CharactersResponseItem
import com.simon.data.models.randomPhoto.RandomPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface NetworkRepository {

    /**
     * @return Flow<> of all characters from the api
     *
     */
    fun getCharacters(): Flow<NetworkResult<List<CharactersResponseItem>>>

    /**
     * @return Flow<Network<Result<RandomPhoto> of a random landscape image
     *
     */
    fun getRandomPhoto(): Flow<NetworkResult<RandomPhoto>>{
        return flow{

        }
    }
}


sealed class NetworkResult<out T>() {

    data class Success<out T>(val data: T) : NetworkResult<T>()
    object Idle: NetworkResult<Nothing>()
    data class Failure<out T>(val exception: String) : NetworkResult<T>()
    object Loading : NetworkResult<Nothing>()
}