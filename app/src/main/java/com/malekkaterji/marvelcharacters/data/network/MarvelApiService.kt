package com.malekkaterji.marvelcharacters.data.network

import com.malekkaterji.marvelcharacters.models.MarvelCharactersResponse
import com.malekkaterji.marvelcharacters.models.MarvelPublicationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApiService {
    @GET("v1/public/characters")
    suspend fun getCharacters(): Response<MarvelCharactersResponse>

    @GET("v1/public/characters/{characterId}/comics")
    suspend fun getCharacterComics(@Path("characterId") characterId: Int): Response<MarvelPublicationResponse>

    @GET("v1/public/characters/{characterId}/stories")
    suspend fun getCharacterStories(@Path("characterId") characterId: Int): Response<MarvelPublicationResponse>

    @GET("v1/public/characters/{characterId}/events")
    suspend fun getCharacterEvents(@Path("characterId") characterId: Int): Response<MarvelPublicationResponse>

    @GET("v1/public/characters/{characterId}/series")
    suspend fun getCharacterSeries(@Path("characterId") characterId: Int): Response<MarvelPublicationResponse>
}