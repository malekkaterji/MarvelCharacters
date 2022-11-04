package com.malekkaterji.marvelcharacters

import androidx.appcompat.widget.AppCompatImageView
import com.squareup.picasso.Picasso

fun AppCompatImageView.loadImage(url: String){
    Picasso.get().load(url).placeholder(R.drawable.marvel_placeholder_thumbnail).into(this)
}
