package com.bychkovdmytro.mornhousetesttask.db

import androidx.lifecycle.*
import com.bychkovdmytro.mornhousetesttask.entities.NumbersList
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(database: MainDataBase): ViewModel() {
    private val dao = database.getDao()
    val allFacts: LiveData<List<NumbersList>> = dao.getAllFacts().asLiveData()

    fun insertFact(fact: NumbersList) = viewModelScope.launch {
        dao.insertFact(fact)
    }

    class MainViewModelFactory(val database: MainDataBase): ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }

    }
}