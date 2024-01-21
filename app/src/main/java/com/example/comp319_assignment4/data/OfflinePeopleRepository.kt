package com.example.comp319_assignment4.data

import kotlinx.coroutines.flow.Flow

class OfflinePeopleRepository(private val peopleDao: PeopleDao) : PeopleRepository {
    override fun getAllPeopleStream(): Flow<List<People>> = peopleDao.getAllPeople()

    override fun getPeopleStream(id: Int): Flow<People?> = peopleDao.getPeople(id)

    override suspend fun insertPeople(people: People) = peopleDao.insert(people)

    override suspend fun deletePeople(people: People) = peopleDao.delete(people)

    override suspend fun updatePeople(people: People) = peopleDao.update(people)
}