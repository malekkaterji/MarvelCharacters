package com.malekkaterji.marvelcharacters.data.network

import com.malekkaterji.marvelcharacters.models.MarvelCharactersResponse
import com.malekkaterji.marvelcharacters.models.MarvelPublicationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApiService {
    @GET("v1/public/characters")
    suspend fun getCharacters(@Query("offset") offset: Int): Response<MarvelCharactersResponse>

    @GET("v1/public/characters/{characterId}/comics")
    suspend fun getCharacterComics(
        @Path("characterId") characterId: Int,
        @Query("limit") limit: Int
        ): Response<MarvelPublicationResponse>

    @GET("v1/public/characters/{characterId}/stories")
    suspend fun getCharacterStories(
        @Path("characterId") characterId: Int,
        @Query("limit") limit: Int
    ): Response<MarvelPublicationResponse>

    @GET("v1/public/characters/{characterId}/events")
    suspend fun getCharacterEvents(
        @Path("characterId") characterId: Int,
        @Query("limit") limit: Int
    ): Response<MarvelPublicationResponse>

    @GET("v1/public/characters/{characterId}/series")
    suspend fun getCharacterSeries(
        @Path("characterId") characterId: Int,
        @Query("limit") limit: Int
    ): Response<MarvelPublicationResponse>
}