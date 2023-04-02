package com.simon.data.models.characters

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Serializable
    @Entity(tableName = CHARACTERS_TABLE)
    @Parcelize
    data class CharactersResponseItem(
        @SerialName("actor")
        val actor: String,
        @SerialName("alive")
        val alive: Boolean,
        @SerialName("alternate_actors")
        val alternateActors: List<String>,
        @SerialName("alternate_names")
        val alternateNames: List<String>,
        @SerialName("ancestry")
        val ancestry: String,
        @SerialName("dateOfBirth")
        val dateOfBirth: String?=null,
        @SerialName("eyeColour")
        val eyeColour: String,
        @SerialName("gender")
        val gender: String,
        @SerialName("hairColour")
        val hairColour: String,
        @SerialName("hogwartsStaff")
        val hogwartsStaff: Boolean,
        @SerialName("hogwartsStudent")
        val hogwartsStudent: Boolean,
        @SerialName("house")
        val house: String,
        @PrimaryKey
        @SerialName("id")
        val id: String,
        @SerialName("image")
        val image: String,
        @SerialName("name")
        val name: String,
        @SerialName("patronus")
        val patronus: String,
        @SerialName("species")
        val species: String,
        @SerialName("wand")
        @Embedded("wand")
        val wand: Wand,
        @SerialName("wizard")
        val wizard: Boolean,
        @SerialName("yearOfBirth")
        val yearOfBirth: Int?=null
    ) : Parcelable

@Serializable
@Parcelize
data class Wand(
    @SerialName("core")
    val core: String,
    @SerialName("length")
    val length: Double?=null,
    @SerialName("wood")
    val wood: String
) : Parcelable

fun String.isMale(): Boolean{
    return this.equals("male",true)
}

fun String.isFeMale(): Boolean{
    return this.equals("female",true)
}

const val CHARACTERS_TABLE = "characters"