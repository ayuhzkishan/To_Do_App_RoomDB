package com.example.todoroom

import androidx.lifecycle.LiveData

class ToDoItemRepository(private val toDoItemDao: ToDoItemDao) {

    val allItems: LiveData<List<ToDoItem>> = toDoItemDao.getAll()

    suspend fun insert(toDoItem: ToDoItem) {
        toDoItemDao.insert(toDoItem)
    }

    suspend fun update(toDoItem: ToDoItem) {
        toDoItemDao.update(toDoItem)
    }

    suspend fun delete(toDoItem: ToDoItem) {
        toDoItemDao.delete(toDoItem)
    }
}