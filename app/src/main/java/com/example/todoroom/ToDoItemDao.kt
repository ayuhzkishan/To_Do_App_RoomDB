package com.example.todoroom

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ToDoItemDao {
    @Query("SELECT * FROM todo_items")
    fun getAll(): LiveData<List<ToDoItem>>

    @Insert
    fun insert(toDoItem: ToDoItem)

    @Update
    fun update(toDoItem: ToDoItem)

    @Delete
    fun delete(toDoItem: ToDoItem)
}