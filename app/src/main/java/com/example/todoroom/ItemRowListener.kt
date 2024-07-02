package com.example.todoroom

interface ItemRowListener {
    fun modifyItemState(itemId: Int, isDone: Boolean)
    fun onItemDelete(itemId: Int)
    fun modifyItem(item: ToDoItem)
}