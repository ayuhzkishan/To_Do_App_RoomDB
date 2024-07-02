package com.example.todoroom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.todoroom.AppDatabase

class ToDoItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ToDoItemRepository
    val allItems: LiveData<List<ToDoItem>>

    init {
        val database = AppDatabase.getDatabase(application.applicationContext)
        val toDoItemDao = database.toDoItemDao()
        repository = ToDoItemRepository(toDoItemDao)
        allItems = repository.allItems
    }

    fun insert(toDoItem: ToDoItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(toDoItem)
    }

    fun update(toDoItem: ToDoItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(toDoItem)
    }

    fun delete(toDoItem: ToDoItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(toDoItem)
    }
}
