package com.malekkaterji.marvelcharacters.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelCharacter(
    val id: Int?,
    val name: String?,
    val description: String?,
    val thumbnailUrl: String?) : Parcelable, MarvelListContent

fun Character.toMarvelCharacter() = MarvelCharacter(
    id = id,
    name = name,
    description = description,
    thumbnailUrl = getURL(thumbnail)
)

fun getURL(thumbnail: Image?): String? {
    if(thumbnail != null && thumbnail.path != null && thumbnail.extension != null && thumbnail.path != "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available"){
        return (thumbnail.path + "." + thumbnail.extension).replace("http", "https")
    } else{
        return null
    }
}