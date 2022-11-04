package com.malekkaterji.marvelcharacters.data.network

import com.malekkaterji.marvelcharacters.models.MarvelCharactersResponse
import retrofit2.Response
import retrofit2.http.GET

interface MarvelApiService {
    @GET("v1/public/characters")
    suspend fun getCharacters(): Response<MarvelCharactersResponse>
}