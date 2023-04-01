package com.simon.harrypotter.core.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simon.data.models.characters.CharactersResponseItem
import com.simon.data.network.repositories.NetworkRepository
import com.simon.data.network.repositories.NetworkResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AppViewModel(private val repository: NetworkRepository):ViewModel() {

    private val _characters = Channel<NetworkResult<List<CharactersResponseItem>>>()
    val characters : Flow<NetworkResult<List<CharactersResponseItem>>>
        get() = _characters.receiveAsFlow()


    fun getAllCharacters() = viewModelScope.launch {
        repository.getCharacters().collect{
            _characters.send(it)
        }
    }



}

