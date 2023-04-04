package com.simon.data.data.local.database

import androidx.room.*
import com.simon.data.models.characters.CharactersResponseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDatabaseDao {

        @Query("SELECT * FROM characters")
        fun getAllCharacters(): Flow<List<CharactersResponseItem>>

        @Query("SELECT * FROM characters WHERE id is :userId")
        fun getCharacter(userId: Int): CharactersResponseItem

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insertAll(vararg users: CharactersResponseItem)

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insertAll( charactersResponseItem: List<CharactersResponseItem>)

        @Delete
        suspend fun delete(user: CharactersResponseItem)

        @Query("DELETE FROM characters")
        suspend fun deleteAll()

}