package com.example.vkcup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


sealed interface  MainUiState{
    class Result(val tags: List<TagItem>) : MainUiState
}

class MainViewModel : ViewModel() {

    private val _stateScreen = MutableLiveData<MainUiState>()
    val stateScreen: LiveData<MainUiState> = _stateScreen

    init {
        loadTags()
    }

    private fun loadTags() {
        viewModelScope.launch {
            TagRepository.getTags().collect { tagItemList ->
                _stateScreen.postValue(MainUiState.Result(tagItemList))
            }
        }
    }

    fun updateTag(updateItem: TagItem) {
        TagRepository.selectTag(updateItem)
    }
}