package com.malekkaterji.marvelcharacters.models

data class MarvelPublication(
    val id: Int?,
    val title: String?,
    val description: String?,
    val thumbnailUrl: String?)

fun Publication.toMarvelPublication() = MarvelPublication(
    id = id,
    title = title,
    description = description,
    thumbnailUrl = getURL(thumbnail)
)

