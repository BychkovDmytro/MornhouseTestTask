package com.bychkovdmytro.mornhousetesttask.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bychkovdmytro.mornhousetesttask.entities.NumbersList
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Query("SELECT * FROM Numbers")
    fun getAllFacts(): Flow<List<NumbersList>>

    @Insert
    suspend fun insertFact(fact: NumbersList)

}