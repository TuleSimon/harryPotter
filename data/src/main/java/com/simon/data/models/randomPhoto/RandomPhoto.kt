package com.simon.data.models.randomPhoto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Serializable
@Parcelize
data class RandomPhoto(
    @SerialName("alt_description")
    val altDescription: String,
    @SerialName("color")
    val color: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("description")
    val description: String,
    @SerialName("id")
    val id: String,
    @SerialName("urls")
    val urls: Urls,
    @SerialName("width")
    val width: Int
) : Parcelable {

    @Serializable
    @Parcelize
    data class Urls(
        @SerialName("full")
        val full: String,
        @SerialName("raw")
        val raw: String,
        @SerialName("regular")
        val regular: String,
        @SerialName("small")
        val small: String,
        @SerialName("small_s3")
        val smallS3: String,
        @SerialName("thumb")
        val thumb: String
    ) : Parcelable

}