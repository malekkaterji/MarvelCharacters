package com.malekkaterji.marvelcharacters.ui.characters.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.malekkaterji.marvelcharacters.data.Repository
import com.malekkaterji.marvelcharacters.models.MarvelPublicationResponse
import com.malekkaterji.marvelcharacters.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MarvelCharacterDetailViewModel @Inject constructor(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application)  {
    var comicsResponse: MutableLiveData<NetworkResult<MarvelPublicationResponse>> = MutableLiveData()
    var storiesResponse: MutableLiveData<NetworkResult<MarvelPublicationResponse>> = MutableLiveData()
    var eventsResponse: MutableLiveData<NetworkResult<MarvelPublicationResponse>> = MutableLiveData()
    var seriesResponse: MutableLiveData<NetworkResult<MarvelPublicationResponse>> = MutableLiveData()

    fun getComics(characterId: Int) = viewModelScope.launch{
        comicsResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getComics(characterId)
            comicsResponse.value = handlePublicationResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            comicsResponse.value = NetworkResult.Error(e.message)
        }
    }

    fun getStories(characterId: Int) = viewModelScope.launch{
        storiesResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getStories(characterId)
            storiesResponse.value = handlePublicationResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            storiesResponse.value = NetworkResult.Error(e.message)
        }
    }

    fun getEvents(characterId: Int) = viewModelScope.launch{
        eventsResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getEvents(characterId)
            eventsResponse.value = handlePublicationResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            eventsResponse.value = NetworkResult.Error(e.message)
        }
    }

    fun getSeries(characterId: Int) = viewModelScope.launch{
        seriesResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getSeries(characterId)
            seriesResponse.value = handlePublicationResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            seriesResponse.value = NetworkResult.Error(e.message)
        }
    }

    private fun handlePublicationResponse(response: Response<MarvelPublicationResponse>): NetworkResult<MarvelPublicationResponse> {
        return when {
            response.isSuccessful -> {
                val publicationsResponse = response.body()
                NetworkResult.Success(publicationsResponse!!)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }
}