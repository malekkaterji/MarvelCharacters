package com.malekkaterji.marvelcharacters.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelCharacter(
    val id: Int?,
    val name: String?,
    val thumbnail: String?) : Parcelable

fun Character.toMarvelCharacter() = MarvelCharacter(
    id = id,
    name = name,
    thumbnail = thumbnail?.path
)