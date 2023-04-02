package com.simon.data.local.database

import androidx.room.*
import com.simon.data.models.characters.CharactersResponseItem

@Dao
interface CharactersDatabaseDao {

        @Query("SELECT * FROM characters")
        fun getAllCharacters(): List<CharactersResponseItem>

        @Query("SELECT * FROM characters WHERE id is :userId")
        fun getCharacter(userId: Int): CharactersResponseItem

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun insertAll(vararg users: CharactersResponseItem)

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun insertAll( charactersResponseItem: List<CharactersResponseItem>)

        @Delete
        fun delete(user: CharactersResponseItem)

}