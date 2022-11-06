package com.malekkaterji.marvelcharacters.data

import com.malekkaterji.marvelcharacters.data.network.MarvelApiService
import com.malekkaterji.marvelcharacters.models.MarvelCharactersResponse
import com.malekkaterji.marvelcharacters.models.MarvelPublicationResponse
import retrofit2.Response
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
    private val apiService: MarvelApiService
) {
    suspend fun getCharacters(): Response<MarvelCharactersResponse> {
        return apiService.getCharacters()
    }

    suspend fun getComics(characterId: Int): Response<MarvelPublicationResponse> {
        return apiService.getCharacterComics(characterId)
    }

    suspend fun getStories(characterId: Int): Response<MarvelPublicationResponse> {
        return apiService.getCharacterStories(characterId)
    }

    suspend fun getEvents(characterId: Int): Response<MarvelPublicationResponse> {
        return apiService.getCharacterEvents(characterId)
    }

    suspend fun getSeries(characterId: Int): Response<MarvelPublicationResponse> {
        return apiService.getCharacterSeries(characterId)
    }

}