package com.malekkaterji.marvelcharacters.ui.characters.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.malekkaterji.marvelcharacters.R
import com.malekkaterji.marvelcharacters.databinding.FragmentCharacterListBinding
import com.malekkaterji.marvelcharacters.hide
import com.malekkaterji.marvelcharacters.makeVisible
import com.malekkaterji.marvelcharacters.models.MarvelCharacter
import com.malekkaterji.marvelcharacters.models.MarvelCharactersResponse
import com.malekkaterji.marvelcharacters.models.toMarvelCharacter
import com.malekkaterji.marvelcharacters.ui.adapters.CharacterRecyclerViewAdapter
import com.malekkaterji.marvelcharacters.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarvelCharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private lateinit var characterListViewModel: MarvelCharacterListViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        characterListViewModel =
            ViewModelProvider(requireActivity())[MarvelCharacterListViewModel::class.java]

        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestMarvelCharacters()
    }

    private fun requestMarvelCharacters() {
        characterListViewModel.charactersResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideLoader()
                    response.data?.let {
                        populateList(it)
                        it.status?.let { it1 -> Log.d("", it1) }
                    }
                }
                is NetworkResult.Error -> {
                    hideLoader()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showLoader()
                }
            }
        }

        characterListViewModel.getCharacters()
    }

    private fun hideLoader() {
        binding.loader?.hide()
    }

    private fun showLoader() {
        binding.loader?.makeVisible()
    }

    private fun populateList(charactersResponse: MarvelCharactersResponse) {
        if(charactersResponse.data != null && charactersResponse.data.count != null && charactersResponse.data.results != null){
            if(charactersResponse.data.count > 0){
                val marvelCharacters = mutableListOf<MarvelCharacter>()
                for(character in charactersResponse.data.results){
                    if(character != null) {
                        marvelCharacters.add(character.toMarvelCharacter())
                    }
                }
                if(marvelCharacters.isNotEmpty()){
                    val recyclerView: RecyclerView = binding.characterList
                    recyclerView.adapter = CharacterRecyclerViewAdapter(
                        marvelCharacters, null)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}