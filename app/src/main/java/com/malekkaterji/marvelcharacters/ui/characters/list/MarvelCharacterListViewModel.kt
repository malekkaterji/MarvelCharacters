package com.malekkaterji.marvelcharacters.ui.characters.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.malekkaterji.marvelcharacters.data.Repository
import com.malekkaterji.marvelcharacters.models.MarvelCharactersResponse
import com.malekkaterji.marvelcharacters.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MarvelCharacterListViewModel @Inject constructor(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application)  {

    var charactersResponse: MutableLiveData<NetworkResult<MarvelCharactersResponse>> = MutableLiveData()

    fun getCharacters(offset: Int) = viewModelScope.launch{
        charactersResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getCharacters(offset)
            charactersResponse.value = handleCharactersResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            charactersResponse.value = NetworkResult.Error(e.message)
        }
    }

    private fun handleCharactersResponse(response: Response<MarvelCharactersResponse>): NetworkResult<MarvelCharactersResponse> {
        return when {
            response.isSuccessful -> {
                val charactersResponse = response.body()
                NetworkResult.Success(charactersResponse!!)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }
}