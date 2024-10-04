package com.example.bottomnavTagli

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog

class CustomAdapter(
    private val context: Context,
    private val itemList: MutableList<ListItem>
) : BaseAdapter() {

    override fun getCount(): Int = itemList.size

    override fun getItem(position: Int): Any = itemList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(context)
        val itemView = convertView ?: layoutInflater.inflate(R.layout.list_item, parent, false)

        val checkBox = itemView.findViewById<CheckBox>(R.id.checkBox)
        val textView = itemView.findViewById<TextView>(R.id.textViewItem)
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)

        val currentItem = itemList[position]

        when (currentItem.type) {
            ItemType.CHECKBOX -> {
                checkBox.visibility = View.VISIBLE
                imageView.visibility = View.GONE
                textView.visibility = View.VISIBLE
                textView.text = currentItem.text
                checkBox.isChecked = currentItem.isChecked
            }
            ItemType.IMAGEVIEW -> {
                checkBox.visibility = View.GONE
                imageView.visibility = View.VISIBLE
                textView.visibility = View.VISIBLE
                textView.text = currentItem.text
            }
            ItemType.TEXTVIEW -> {
                checkBox.visibility = View.GONE
                imageView.visibility = View.GONE
                textView.visibility = View.VISIBLE
                textView.text = currentItem.text
            }
        }

        itemView.setOnClickListener(object : View.OnClickListener {
            private var lastTapTime: Long = 0
            override fun onClick(v: View) {
                val currentTapTime = System.currentTimeMillis()
                if (currentTapTime - lastTapTime < 300) { // Double-click identified
                    openEditOrDeleteDialog(position)
                }
                lastTapTime = currentTapTime
            }
        })

        return itemView
    }

    private fun openEditOrDeleteDialog(position: Int) {
        val selectedItem = itemList[position]
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Modify or Remove Item")
        dialogBuilder.setMessage("What action would you like to take for this item?")

        dialogBuilder.setPositiveButton("Edit") { _, _ ->
            // Logic for modifying the item
            val inputField = EditText(context)
            inputField.setText(selectedItem.text)
            AlertDialog.Builder(context)
                .setTitle("Update Item")
                .setView(inputField)
                .setPositiveButton("OK") { _, _ ->
                    selectedItem.text = inputField.text.toString()
                    notifyDataSetChanged()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        dialogBuilder.setNegativeButton("Delete") { _, _ ->
            // Remove the selected item
            itemList.removeAt(position)
            notifyDataSetChanged()
        }

        dialogBuilder.setNeutralButton("Cancel", null)
        dialogBuilder.show()
    }
}