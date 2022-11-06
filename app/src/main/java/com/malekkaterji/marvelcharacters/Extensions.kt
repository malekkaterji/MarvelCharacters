package com.malekkaterji.marvelcharacters

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.squareup.picasso.Picasso

fun AppCompatImageView.loadImage(url: String){
    Picasso.get().load(url).placeholder(R.drawable.marvel_placeholder_thumbnail).into(this)
}

fun View.hide(){
    visibility = View.GONE
}

fun View.makeVisible(){
    visibility = View.VISIBLE
}