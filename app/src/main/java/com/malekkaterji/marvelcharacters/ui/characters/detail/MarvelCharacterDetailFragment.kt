package com.malekkaterji.marvelcharacters.ui.characters.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.malekkaterji.marvelcharacters.R
import com.malekkaterji.marvelcharacters.databinding.FragmentCharacterDetailBinding
import com.malekkaterji.marvelcharacters.hide
import com.malekkaterji.marvelcharacters.loadImage
import com.malekkaterji.marvelcharacters.makeVisible
import com.malekkaterji.marvelcharacters.models.MarvelCharacter
import com.malekkaterji.marvelcharacters.models.MarvelPublication
import com.malekkaterji.marvelcharacters.models.MarvelPublicationResponse
import com.malekkaterji.marvelcharacters.models.toMarvelPublication
import com.malekkaterji.marvelcharacters.ui.adapters.PublicationRecyclerViewAdapter
import com.malekkaterji.marvelcharacters.util.NetworkResult

class MarvelCharacterDetailFragment : Fragment() {

    private var marvelCharacter: MarvelCharacter? = null

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var characterDetailViewModel: MarvelCharacterDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_CHARACTER)) {
                marvelCharacter = it.getParcelable(ARG_CHARACTER)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        characterDetailViewModel =
            ViewModelProvider(requireActivity())[MarvelCharacterDetailViewModel::class.java]

        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        prepareLayout()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(marvelCharacter != null && marvelCharacter?.id != null) {
            requestMarvelCharacterPublications()
        }
    }

    private fun prepareLayout(){
        // Character name - ID
        if(marvelCharacter?.name != null && marvelCharacter?.id != null) {
            binding.detailToolbar?.title = marvelCharacter!!.name + " - " + marvelCharacter!!.id
        }

        // Character thumbnail
        if(marvelCharacter?.thumbnailUrl != null) {
            binding.characterThumbnail?.loadImage(marvelCharacter!!.thumbnailUrl!!)
        }else{
            binding.characterThumbnail?.setImageResource(R.drawable.marvel_default_thumbnail)
        }

        // Character description
        if(!marvelCharacter?.description.isNullOrEmpty()){
            binding.characterDescription?.text = marvelCharacter?.description
        }else{
            // No description available for character -> hide description view
            binding.characterDescriptionLayout?.visibility = View.GONE
        }
    }

    private fun requestMarvelCharacterPublications() {
        setObserver(characterDetailViewModel.comicsResponse, binding.comicList, binding.comicLoader, binding.comicMessage, getString(R.string.character_comics).lowercase())
        setObserver(characterDetailViewModel.storiesResponse, binding.storyList, binding.storyLoader, binding.storyMessage, getString(R.string.character_stories).lowercase())
        setObserver(characterDetailViewModel.eventsResponse, binding.eventList, binding.eventLoader, binding.eventMessage, getString(R.string.character_events).lowercase())
        setObserver(characterDetailViewModel.seriesResponse, binding.seriesList, binding.seriesLoader, binding.seriesMessage, getString(R.string.character_series).lowercase())

        characterDetailViewModel.getComics(marvelCharacter!!.id!!)
        characterDetailViewModel.getStories(marvelCharacter!!.id!!)
        characterDetailViewModel.getEvents(marvelCharacter!!.id!!)
        characterDetailViewModel.getSeries(marvelCharacter!!.id!!)
    }

    private fun setObserver(
        response: MutableLiveData<NetworkResult<MarvelPublicationResponse>>,
        recyclerView: RecyclerView?,
        loader: LottieAnimationView?,
        messageView: TextView?,
        publicationTypeText: String
    ){
        response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                   hideLoading(loader, messageView)
                    response.data?.let {
                        populateList(it, recyclerView, messageView, publicationTypeText)
                        it.status?.let { it1 -> Log.d("", it1) }
                    }
                }
                is NetworkResult.Error -> {
                    hideLoading(loader, messageView)
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showLoading(loader, messageView, publicationTypeText)
                }
            }
        }
    }

    private fun showLoading(
        loader: LottieAnimationView?,
        messageView: TextView?,
        publicationTypeText: String
    ){
        loader?.makeVisible()
        messageView?.makeVisible()
        messageView?.text = resources.getString(R.string.loading_publications, publicationTypeText)
    }

    private fun hideLoading(
        loader: LottieAnimationView?,
        messageView: TextView?
    ){
        loader?.hide()
        messageView?.hide()
        messageView?.text = ""
    }

    private fun populateList(
        publicationResponse: MarvelPublicationResponse,
        recyclerView :RecyclerView?,
        messageView: TextView?,
        publicationTypeText: String
    ) {
        if(publicationResponse.data?.count != null && publicationResponse.data.results != null){
            if(publicationResponse.data.count > 0){
                val marvelPublications = mutableListOf<MarvelPublication>()
                for(publication in publicationResponse.data.results){
                    if(publication != null) {
                        marvelPublications.add(publication.toMarvelPublication())
                    }
                }
                if(marvelPublications.isNotEmpty()){
                    if(recyclerView != null){
                        recyclerView.adapter = PublicationRecyclerViewAdapter(
                            marvelPublications)
                    }
                }
            }else{
                messageView?.text = getString(R.string.no_publications, publicationTypeText)
                messageView?.makeVisible()
            }
        }
    }

    companion object {
        const val ARG_CHARACTER = "character"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}