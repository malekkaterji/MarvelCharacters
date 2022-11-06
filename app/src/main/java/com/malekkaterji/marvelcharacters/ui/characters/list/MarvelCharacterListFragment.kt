package com.malekkaterji.marvelcharacters.ui.characters.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.malekkaterji.marvelcharacters.databinding.FragmentCharacterListBinding
import com.malekkaterji.marvelcharacters.hide
import com.malekkaterji.marvelcharacters.makeVisible
import com.malekkaterji.marvelcharacters.models.*
import com.malekkaterji.marvelcharacters.ui.adapters.CharacterRecyclerViewAdapter
import com.malekkaterji.marvelcharacters.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarvelCharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private lateinit var characterListViewModel: MarvelCharacterListViewModel

    private val binding get() = _binding!!
    private val marvelCharacters = mutableListOf<MarvelListContent>()
    private var requestingCharacter = false

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

        binding.characterList.adapter = CharacterRecyclerViewAdapter(
            marvelCharacters, null)
        initScrollListener()
        setObserver()
        requestMarvelCharacters()
    }

    private fun initScrollListener() {
        binding.characterList.addOnScrollListener(object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (recyclerView.layoutManager is LinearLayoutManager){
                if((recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == marvelCharacters.size - 1){
                    if(!requestingCharacter) {
                        requestMarvelCharacters()
                    }
                }
            }
        }})
    }

    private fun setObserver(){
        characterListViewModel.charactersResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    doneLoading()
                    response.data?.let {
                        populateList(it)
                        it.status?.let { it1 -> Log.d("", it1) }
                    }
                }
                is NetworkResult.Error -> {
                    doneLoading()
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
    }

    private fun requestMarvelCharacters() {
        requestingCharacter = true
        if(marvelCharacters.size == 0){
            characterListViewModel.getCharacters(0)
        }else{
            characterListViewModel.getCharacters(marvelCharacters.size - 1)
        }
    }

    private fun doneLoading() {
        binding.loader?.hide()
        requestingCharacter = false
    }

    private fun showLoader() {
        binding.loader?.makeVisible()
    }

    private fun populateList(charactersResponse: MarvelCharactersResponse) {
        if(charactersResponse.data != null && charactersResponse.data.count != null && charactersResponse.data.total != null && charactersResponse.data.results != null){
            if(charactersResponse.data.count > 0){
                val initialSize = marvelCharacters.size

                if(initialSize > 0){
                    if(marvelCharacters.last() is MarvelListCount) {
                        marvelCharacters.removeLast()
                    }
                }

                for(character in charactersResponse.data.results){
                    if(character != null) {
                        marvelCharacters.add(character.toMarvelCharacter())
                    }
                }
                if(marvelCharacters.isNotEmpty()){
                    marvelCharacters.add(MarvelListCount(marvelCharacters.size, charactersResponse.data.total))
                        binding.characterList.adapter?.notifyItemRangeChanged(initialSize, charactersResponse.data.count)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}