package com.simon.data.data.local

import com.simon.data.data.local.database.CharactersDatabaseDao
import com.simon.data.models.characters.CharactersResponseItem
import com.simon.data.data.network.repositories.NetworkRepository
import com.simon.data.data.network.repositories.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class LocalRepository(private val dao: CharactersDatabaseDao): NetworkRepository {
    override fun getCharacters(): Flow<NetworkResult<List<CharactersResponseItem>>> =flow{
        dao.getAllCharacters().collect{
            if(it.isEmpty()){
                emit(NetworkResult.Failure("No data yet"))
            }
            else{
                emit(NetworkResult.Success(it))
            }
        }
    }

    suspend fun saveCharacters(onlineData: List<CharactersResponseItem>) {
        dao.insertAll(onlineData)
    }

    suspend fun deleteCharacters(onlineData: CharactersResponseItem) {
        dao.delete(onlineData)
    }

    suspend fun deleteAllCharacters() {
        dao.deleteAll()
    }


}