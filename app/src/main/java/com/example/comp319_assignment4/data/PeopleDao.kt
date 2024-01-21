package com.example.comp319_assignment4.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PeopleDao {

    @Query("SELECT * from people ORDER BY name ASC")
    fun getAllPeople(): Flow<List<People>>

    @Query("SELECT * from people WHERE id = :id")
    fun getPeople(id: Int): Flow<People>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(people: People)

    @Update
    suspend fun update(people: People)

    @Delete
    suspend fun delete(people: People)
}