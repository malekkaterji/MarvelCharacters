package com.malekkaterji.marvelcharacters.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.malekkaterji.marvelcharacters.ui.characters.detail.MarvelCharacterDetailFragment
import com.malekkaterji.marvelcharacters.R
import com.malekkaterji.marvelcharacters.databinding.CharacterListContentBinding
import com.malekkaterji.marvelcharacters.databinding.CharacterListCountBinding
import com.malekkaterji.marvelcharacters.loadImage
import com.malekkaterji.marvelcharacters.models.MarvelCharacter
import com.malekkaterji.marvelcharacters.models.MarvelListContent
import com.malekkaterji.marvelcharacters.models.MarvelListCount

class CharacterRecyclerViewAdapter(
    private val characterList: List<MarvelListContent>,
    private val characterDetailFragmentContainer: View?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val MARVEL_LIST_CONTENT_TYPE = 0;
    val MARVEL_LIST_COUNT_TYPE = 1;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            val binding =
                CharacterListCountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CharacterCountViewHolder(binding)
        }else{
            val binding =
                CharacterListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CharacterContentViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val marvelListContent = characterList[position]
        if(marvelListContent is MarvelCharacter && holder is CharacterContentViewHolder) {

            // Character name
            holder.characterNameTextView.text = marvelListContent.name

            // Character thumbnail
            if (marvelListContent.thumbnailUrl != null) {
                holder.characterThumbnailImageView.loadImage(marvelListContent.thumbnailUrl)
            } else {
                holder.characterThumbnailImageView.setImageResource(R.drawable.marvel_default_thumbnail)
            }

            // Set on click listener
            with(holder.itemView) {
                tag = marvelListContent
                setOnClickListener { itemView ->
                    val bundle = Bundle()
                    bundle.putParcelable(
                        MarvelCharacterDetailFragment.ARG_CHARACTER,
                        marvelListContent
                    )
                    if (characterDetailFragmentContainer != null) {
                        characterDetailFragmentContainer.findNavController()
                            .navigate(R.id.fragment_item_detail, bundle)
                    } else {
                        itemView.findNavController().navigate(R.id.show_character_detail, bundle)
                    }
                }
            }
        }else if(marvelListContent is MarvelListCount && holder is CharacterCountViewHolder){
            // Character list count & total
            holder.descriptionTextView.text = holder.itemView.context.getString(R.string.character_list_showing_count, marvelListContent.count, marvelListContent.total)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val marvelListContent = characterList[position]
        if(marvelListContent is MarvelListCount){
            return MARVEL_LIST_COUNT_TYPE
        }else {
            return MARVEL_LIST_CONTENT_TYPE;
        }
    }

    override fun getItemCount() = characterList.size

    inner class CharacterContentViewHolder(binding: CharacterListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val characterThumbnailImageView: AppCompatImageView = binding.characterThumbnail
        val characterNameTextView: TextView = binding.characterName
    }

    inner class CharacterCountViewHolder(binding: CharacterListCountBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val descriptionTextView: TextView = binding.description
    }

}