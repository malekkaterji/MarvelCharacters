package com.malekkaterji.marvelcharacters.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MarvelPublicationResponse(
    @SerializedName("code") val code: Int?,
    @SerializedName("status") val status: String?,
    @SerializedName("copyright") val copyright: String?,
    @SerializedName("attributionText") val attributionText: String?,
    @SerializedName("attributionHTML") val attributionHTML: String?,
    @SerializedName("etag") val etag: String?,
    @SerializedName("data") val data: PublicationData?
)

@Keep
data class PublicationData(
    @SerializedName("offset") val offset: Int?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("total") val total: Int?,
    @SerializedName("count") val count: Int?,
    @SerializedName("results") val results: List<Publication?>?
)

@Keep
data class Publication(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("thumbnail") val thumbnail: Image?
)

