package com.example.todoroom
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView

class ToDoItemAdapter(
    private val context: Context,
    var itemList: MutableList<ToDoItem>,
    private val editItemListener: (ToDoItem) -> Unit,
    private val deleteItemListener: (ToDoItem) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = itemList.size

    override fun getItem(position: Int): ToDoItem = itemList[position]

    override fun getItemId(position: Int): Long = itemList[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.task_layout, parent, false)
        val holder = ListRowHolder(rowView)

        val item = getItem(position)
        holder.label.text = item.itemText
        holder.isDone.isChecked = item.done

        holder.editButton.setOnClickListener { editItemListener(item) }
        holder.ibDeleteObject.setOnClickListener { deleteItemListener(item) }

        return rowView
    }

    private class ListRowHolder(row: View) {
        val label: TextView = row.findViewById(R.id.tv_item_text)
        val isDone: CheckBox = row.findViewById(R.id.cb_item_is_done)
        val ibDeleteObject: ImageButton = row.findViewById(R.id.iv_cross)
        val editButton: ImageButton = row.findViewById(R.id.edit_button)
    }
}

