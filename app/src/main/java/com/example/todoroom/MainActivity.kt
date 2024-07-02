package com.example.todoroom

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() , ItemRowListener{

    private lateinit var listViewItems: ListView
    private lateinit var database: AppDatabase
    private lateinit var toDoItemDao: ToDoItemDao
    private lateinit var adapter: ToDoItemAdapter
    private lateinit var viewModel: ToDoItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewItems = findViewById(R.id.items_list)

        val headerView = layoutInflater.inflate(R.layout.list_header, listViewItems, false)
        listViewItems?.addHeaderView(headerView)

        val emptyAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, emptyList<String>())
        listViewItems?.adapter = emptyAdapter

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        adapter = ToDoItemAdapter(
            this,
            mutableListOf(),
            editItemListener = { item -> showEditItemDialog(item) },
            deleteItemListener = { item -> onItemDelete(item.id) }
        )
        listViewItems?.adapter = adapter

        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "todo-db").build()
        toDoItemDao = database.toDoItemDao()

        viewModel = ViewModelProvider(this).get(ToDoItemViewModel::class.java)
        viewModel.allItems.observe(this, Observer { items ->
            items?.let {
                adapter.itemList.clear()
                adapter.itemList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })


        fab.setOnClickListener { view ->
            //Show Dialog here to add new Item
            addNewItemDialog()
        }


    }

    private fun addNewItemDialog() {
        val alert = AlertDialog.Builder(this)
        val itemEditText = EditText(this)
        alert.setMessage("Add New Item")
        alert.setTitle("Enter To Do Item Text")
        alert.setView(itemEditText)
        alert.setPositiveButton("Submit") { dialog, positiveButton ->
            val todoItem = ToDoItem(itemText = itemEditText.text.toString(), done = false)
            viewModel.insert(todoItem)
            dialog.dismiss()
        }
        alert.show()
    }

    private fun showEditItemDialog(item: ToDoItem) {
        val alert = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.new_task, null)
        alert.setView(dialogView)
        val itemEditText = dialogView.findViewById<EditText>(R.id.newTaskText)
        val saveButton = dialogView.findViewById<Button>(R.id.newTaskButton)

        itemEditText.setText(item.itemText)
        val dialog = alert.create()

        saveButton.setOnClickListener {
            val newItemText = itemEditText.text.toString()
            if (newItemText.isNotEmpty()) {
                item.itemText = newItemText
                viewModel.update(item)
                dialog.dismiss()
            }
        }
        dialog.show()
    }
    override fun onItemDelete(itemId: Int) {
        val item = adapter.itemList.find { it.id == itemId }
        item?.let {
            adapter.itemList.remove(it)
            viewModel.delete(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun modifyItem(item: ToDoItem) {
        viewModel.update(item)
    }

    override fun modifyItemState(itemId: Int, isDone: Boolean) {
        val item = adapter.itemList.find { it.id == itemId }
        item?.let {
            it.done = isDone
            viewModel.update(it)
        }
    }
}