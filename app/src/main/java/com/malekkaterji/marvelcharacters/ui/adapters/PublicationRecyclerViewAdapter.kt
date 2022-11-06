package com.malekkaterji.marvelcharacters.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.malekkaterji.marvelcharacters.R
import com.malekkaterji.marvelcharacters.databinding.PublicationListContentBinding
import com.malekkaterji.marvelcharacters.loadImage
import com.malekkaterji.marvelcharacters.models.MarvelPublication

class PublicationRecyclerViewAdapter(
    private val publicationList: List<MarvelPublication>
) :
    RecyclerView.Adapter<PublicationRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationRecyclerViewAdapter.ViewHolder {
        val binding =
            PublicationListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PublicationRecyclerViewAdapter.ViewHolder, position: Int) {
        val publication = publicationList[position]

        // publication name
        holder.publicationTitleTextView.text = publication.title

        // publication thumbnail
        if(publication.thumbnailUrl != null) {
            holder.publicationThumbnailImageView.loadImage(publication.thumbnailUrl)
        }else{
            holder.publicationThumbnailImageView.setImageResource(R.drawable.marvel_default_thumbnail)
        }
    }

    override fun getItemCount() = publicationList.size

    inner class ViewHolder(binding: PublicationListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val publicationThumbnailImageView: AppCompatImageView = binding.publicationThumbnail
        val publicationTitleTextView: TextView = binding.publicationTitle
    }

}