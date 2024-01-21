package com.example.comp319_assignment4.data

import kotlinx.coroutines.flow.Flow

interface PeopleRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllPeopleStream(): Flow<List<People>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getPeopleStream(id: Int): Flow<People?>

    /**
     * Insert item in the data source
     */
    suspend fun insertPeople(people: People)

    /**
     * Delete item from the data source
     */
    suspend fun deletePeople(people: People)

    /**
     * Update item in the data source
     */
    suspend fun updatePeople(people: People)
}