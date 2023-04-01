package com.simon.harrypotter.core.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simon.data.models.characters.CharactersResponseItem
import com.simon.data.network.repositories.NetworkRepository
import com.simon.data.network.repositories.NetworkResult
import com.simon.harrypotter.ui.navGraph.Screen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class AppViewModel(private val repository: NetworkRepository):ViewModel() {

    private val _characters = Channel<NetworkResult<List<CharactersResponseItem>>>()
    val characters : Flow<NetworkResult<List<CharactersResponseItem>>>
        get() = _characters.receiveAsFlow()

    fun getAllCharacters() = viewModelScope.launch {
        repository.getCharacters().collect{
            Timber.v(it.toString())
            _characters.send(it)
        }
    }


    private val _uiEvents = Channel<Events>()
    val uiEvents : Flow<Events>
        get() = _uiEvents.receiveAsFlow()
    fun performEvent(event: Events) = viewModelScope.launch{
        Timber.v(event.toString())
            _uiEvents.send(event)
    }

    val searchedCharacters = _characters.receiveAsFlow().combine(_uiEvents.receiveAsFlow()){characters,events ->
        if(events is Events.Search && characters is NetworkResult.Success ){
            val searchedText = events.searchText
            Timber.v(searchedText)
            return@combine characters.data.filter { it.name.contains(searchedText) || it.house.contains(searchedText)}
        }
        else{
            emptyList()
        }
    }

    val showDropDown = uiEvents.combine(searchedCharacters){event, searchCharacters ->
        if(event is Events.Search && searchCharacters.isNotEmpty()){
            return@combine true
        }
        else{
            false
        }

    }



}

sealed class Events(){
    data class Search(val searchText: String):Events()
    object Idle:Events()

    data class NavigateToScreen(val screen:Screen):Events()

}
