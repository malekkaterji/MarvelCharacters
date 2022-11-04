package com.malekkaterji.marvelcharacters.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

typealias Comics = Category
typealias Stories = Category
typealias Events = Category
typealias Series = Category

@Keep
data class MarvelCharactersResponse(
    @SerializedName("code") val code: Int?,
    @SerializedName("status") val status: String?,
    @SerializedName("copyright") val copyright: String?,
    @SerializedName("attributionText") val attributionText: String?,
    @SerializedName("attributionHTML") val attributionHTML: String?,
    @SerializedName("etag") val etag: String?,
    @SerializedName("data") val data: Data?
)

@Keep
data class Data(
    @SerializedName("offset") val offset: Int?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("total") val total: Int?,
    @SerializedName("count") val count: Int?,
    @SerializedName("results") val results: List<Character?>?
)

@Keep
data class Character(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("modified") val modified: String?,
    @SerializedName("thumbnail") val thumbnail: Image?,
    @SerializedName("resourceURI") val resourceURI: String?,
    @SerializedName("comics") val comics: Comics?,
    @SerializedName("stories") val stories: Stories?,
    @SerializedName("events") val events: Events?,
    @SerializedName("series") val series: Series?,
    @SerializedName("urls") val urls: List<Urls>?
)

@Keep
data class Urls(
    @SerializedName("type") val type: String?,
    @SerializedName("url") val url: String?
)

@Keep
data class Image(
    @SerializedName("path") val path: String?,
    @SerializedName("extension") val extension: String?
)

@Keep
data class Category(
    @SerializedName("available") val available: Int?,
    @SerializedName("collectionURI") val collectionURI: String?,
    @SerializedName("items") val items: List<ItemSummary>?,
    @SerializedName("returned") val returned: Int?
)

@Keep
data class ItemSummary(
    @SerializedName("resourceURI") val resourceURI: String?,
    @SerializedName("name") val name: String?
)
