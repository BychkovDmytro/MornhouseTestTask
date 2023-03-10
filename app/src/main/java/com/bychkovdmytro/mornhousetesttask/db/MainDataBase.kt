package com.bychkovdmytro.mornhousetesttask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bychkovdmytro.mornhousetesttask.entities.NumbersList

@Database(entities = [NumbersList::class], version = 1)
abstract class MainDataBase : RoomDatabase() {

    abstract fun getDao(): Dao

    companion object{
        @Volatile
        private var INSTANCE : MainDataBase? = null

        fun getDataBase(context: Context):MainDataBase{

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, MainDataBase::class.java, "numbers_db").build()
                instance
            }
        }
    }
}