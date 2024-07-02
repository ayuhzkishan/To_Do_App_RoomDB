package com.example.todoroom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_items")
data class ToDoItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var itemText: String,
    var done: Boolean
)
