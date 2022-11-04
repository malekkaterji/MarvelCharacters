package com.malekkaterji.marvelcharacters.data

import com.malekkaterji.marvelcharacters.data.network.MarvelApiService
import com.malekkaterji.marvelcharacters.models.MarvelCharactersResponse
import retrofit2.Response
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
    private val apiService: MarvelApiService
) {
    suspend fun getCharacters(): Response<MarvelCharactersResponse> {
        return apiService.getCharacters()
    }
}