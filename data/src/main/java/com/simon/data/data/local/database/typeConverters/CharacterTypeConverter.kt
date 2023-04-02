package com.simon.data.data.local.database.typeConverters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken


@ProvidedTypeConverter
class CharacterTypeConverter(private val jsonParser: JsonParser) {

    @TypeConverter
    fun fromModelCampaignJson(json: String): List<String> {
        return jsonParser.fromJson<ArrayList<String>>(
            json,
            object : TypeToken<ArrayList<String>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toModelCampaignJson(events: List<String>): String {
        return jsonParser.toJson(events, object : TypeToken<ArrayList<String>>() {}.type)
            ?: "[]"
    }
}
