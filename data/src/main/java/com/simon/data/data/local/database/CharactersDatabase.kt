package com.simon.data.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.simon.data.data.local.database.typeConverters.CharacterTypeConverter
import com.simon.data.models.characters.CharactersResponseItem


@Database(
    entities = [CharactersResponseItem::class],
    version = 1
)
@TypeConverters(CharacterTypeConverter::class)
abstract class CharactersDatabase : RoomDatabase() {

    abstract val dao: CharactersDatabaseDao

}