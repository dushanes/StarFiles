package com.starfiles.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.starfiles.data.CharactersPagingSource
import com.starfiles.data.models.Character
import kotlinx.coroutines.flow.Flow

class CharactersListRepository (){
    fun getCharacters(query: String): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { CharactersPagingSource(query) }
        ).flow
    }
}