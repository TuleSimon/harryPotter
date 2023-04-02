package com.simon.data.data.datasource

import com.simon.data.data.local.LocalRepository
import com.simon.data.data.network.repositories.NetworkRepository
import com.simon.data.data.network.repositories.NetworkRepositoryImpl
import com.simon.data.data.network.repositories.NetworkResult
import com.simon.data.models.characters.CharactersResponseItem
import com.simon.data.models.randomPhoto.RandomPhoto
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.androidx.compose.get
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class CharactersDataSource(): NetworkRepository,KoinComponent {

    private val localRepository:LocalRepository by inject()
    private val networkRepositoryImpl: NetworkRepositoryImpl by inject()

    override fun getCharacters(): Flow<NetworkResult<List<CharactersResponseItem>>> =flow{
        emit(NetworkResult.Loading)
        localRepository.getCharacters().collect{offline ->
            if(offline is NetworkResult.Success){
                emit(offline)
                //check if online data has any new data
                networkRepositoryImpl.getCharacters().collect{online ->
                    if(online is NetworkResult.Success){
                        val onlineData = online.data
                        val offlineData = offline.data
                        if(onlineData!=offlineData){
                            emit(online)
                            localRepository.saveCharacters(onlineData)
                        }
                    }
                }
            }
            else{
                networkRepositoryImpl.getCharacters().collect{
                    emit(it)
                    if(it is NetworkResult.Success){
                        val onlineData = it.data
                        if(onlineData.isNotEmpty()){
                            localRepository.saveCharacters(onlineData)
                        }
                    }
                }
            }
        }
    }

    override fun getRandomPhoto(): Flow<NetworkResult<RandomPhoto>> {
        return networkRepositoryImpl.getRandomPhoto()
    }
}