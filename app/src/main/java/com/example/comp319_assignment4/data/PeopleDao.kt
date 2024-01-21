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

    @Query("SELECT * from peoples ORDER BY name ASC")
    fun getAllPeople(): Flow<List<People>>

    @Query("SELECT * from peoples WHERE id = :id")
    fun getPeople(id: Int): Flow<People>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(people: People)

    @Update
    suspend fun update(people: People)

    @Delete
    suspend fun delete(people: People)
}