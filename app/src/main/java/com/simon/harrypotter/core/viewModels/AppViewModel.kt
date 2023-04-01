package com.simon.harrypotter.core.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simon.data.models.characters.CharactersResponseItem
import com.simon.data.network.repositories.NetworkRepository
import com.simon.data.network.repositories.NetworkResult
import com.simon.harrypotter.ui.navGraph.Screen
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class AppViewModel(private val repository: NetworkRepository):ViewModel() {

    private val _characters = MutableStateFlow<NetworkResult<List<CharactersResponseItem>>>(NetworkResult.Idle)
    val characters : Flow<NetworkResult<List<CharactersResponseItem>>>
        get() = _characters

    fun getAllCharacters() = viewModelScope.launch {
        repository.getCharacters().collect{
            Timber.v(it.toString())
            _characters.value=(it)
        }
    }


    private val _uiEvents = MutableSharedFlow<Events>()
    val uiEvents : SharedFlow<Events>
        get() = _uiEvents.asSharedFlow()
    fun performEvent(event: Events) = viewModelScope.launch{
        Timber.v(event.toString())
            _uiEvents.emit(event)
    }

    val searchedCharacters = _characters.combine(uiEvents){ characters, events ->
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

