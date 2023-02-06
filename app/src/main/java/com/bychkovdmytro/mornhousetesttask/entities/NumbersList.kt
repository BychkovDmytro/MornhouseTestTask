package com.bychkovdmytro.mornhousetesttask.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Numbers")
data class NumbersList (

    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "number_fact")
    val number_fact: String

) : Serializable
