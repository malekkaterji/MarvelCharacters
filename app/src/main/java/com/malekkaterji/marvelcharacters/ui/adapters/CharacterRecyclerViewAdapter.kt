package com.malekkaterji.marvelcharacters.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.malekkaterji.marvelcharacters.ui.characters.detail.MarvelCharacterDetailFragment
import com.malekkaterji.marvelcharacters.R
import com.malekkaterji.marvelcharacters.databinding.CharacterListContentBinding
import com.malekkaterji.marvelcharacters.loadImage
import com.malekkaterji.marvelcharacters.models.MarvelCharacter

class CharacterRecyclerViewAdapter(
    private val characterList: List<MarvelCharacter>,
    private val characterDetailFragmentContainer: View?
) :
    RecyclerView.Adapter<CharacterRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            CharacterListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characterList[position]

        // Character name
        holder.characterNameTextView.text = character.name

        // Character thumbnail
        if(character.thumbnailUrl != null) {
            holder.characterThumbnailImageView.loadImage(character.thumbnailUrl)
        }else{
            holder.characterThumbnailImageView.setImageResource(R.drawable.marvel_default_thumbnail)
        }

        // Set on click listener
        with(holder.itemView) {
            tag = character
            setOnClickListener { itemView ->
                val bundle = Bundle()
                bundle.putParcelable(
                    MarvelCharacterDetailFragment.ARG_CHARACTER,
                    character
                )
                if (characterDetailFragmentContainer != null) {
                    characterDetailFragmentContainer.findNavController()
                        .navigate(R.id.fragment_item_detail, bundle)
                } else {
                    itemView.findNavController().navigate(R.id.show_character_detail, bundle)
                }
            }
        }
    }

    override fun getItemCount() = characterList.size

    inner class ViewHolder(binding: CharacterListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val characterThumbnailImageView: AppCompatImageView = binding.characterThumbnail
        val characterNameTextView: TextView = binding.characterName
    }

}