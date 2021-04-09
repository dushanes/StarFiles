package com.starfiles.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.starfiles.api.ApiUtilities
import com.starfiles.data.models.Character
import com.starfiles.data.models.Results
import retrofit2.await

class CharactersPagingSource(private val query: String): PagingSource<Int, Character>() {
    private val apiUtilities: ApiUtilities by lazy { ApiUtilities }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        Log.d("loading list", "page: ${params.key}")
        //on first load key is null, subsequent loads next key in load result is used
        val position: Int = params.key?.toInt() ?: 1

        val response: Results = apiUtilities.getCharactersByPage(position, query).await()
        val characters = response.results

        //must be null or blank, gson can return null next
        val next = if (response.next.isNullOrBlank()) null else position+1

        return LoadResult.Page(
            characters,
            prevKey = if (position == 1) null else position - 1,
            nextKey = next
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}