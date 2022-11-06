package com.malekkaterji.marvelcharacters.data

import com.malekkaterji.marvelcharacters.data.network.MarvelApiService
import com.malekkaterji.marvelcharacters.models.MarvelCharactersResponse
import com.malekkaterji.marvelcharacters.models.MarvelPublicationResponse
import retrofit2.Response
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
    private val apiService: MarvelApiService
) {
    private val defaultPublicationLimit = 3

    suspend fun getCharacters(offset: Int): Response<MarvelCharactersResponse> {
        return apiService.getCharacters(offset)
    }

    suspend fun getComics(characterId: Int): Response<MarvelPublicationResponse> {
        return apiService.getCharacterComics(characterId, defaultPublicationLimit)
    }

    suspend fun getStories(characterId: Int): Response<MarvelPublicationResponse> {
        return apiService.getCharacterStories(characterId, defaultPublicationLimit)
    }

    suspend fun getEvents(characterId: Int): Response<MarvelPublicationResponse> {
        return apiService.getCharacterEvents(characterId, defaultPublicationLimit)
    }

    suspend fun getSeries(characterId: Int): Response<MarvelPublicationResponse> {
        return apiService.getCharacterSeries(characterId, defaultPublicationLimit)
    }

}