package com.starfiles.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.starfiles.data.models.Character
import com.starfiles.repositories.CharactersListRepository
import kotlinx.coroutines.flow.Flow

class CharactersListViewModel(): ViewModel() {
    private var charactersListRepository: CharactersListRepository = CharactersListRepository()
    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Character>>? = null

    fun getCharacters(query: String): Flow<PagingData<Character>>{
        val lastResult = currentSearchResult
        if(query == currentQueryValue && lastResult != null){
            return lastResult
        }
        currentQueryValue = query
        val newResult: Flow<PagingData<Character>> = charactersListRepository.getCharacters(query)
        currentSearchResult = newResult
        return newResult
    }
}