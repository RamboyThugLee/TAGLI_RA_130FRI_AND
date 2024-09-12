package com.example.tasklist

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.ucb.myapplication.R

class TaskAdapter(private val context: Context, private val taskList: ArrayList<String>) :
    ArrayAdapter<String>(context, 0, taskList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.task_item, parent, false)

        val taskTextView = view.findViewById<TextView>(R.id.taskText)
        val checkBox = view.findViewById<CheckBox>(R.id.taskCheckBox)
        val imageView = view.findViewById<ImageView>(R.id.taskImage)

        val task = taskList[position]
        taskTextView.text = task

        // Set checkbox logic to strike through text when checked
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            taskTextView.paintFlags = if (isChecked) {
                taskTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                taskTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        // Handle double-click to open Edit/Delete dialog
        view.setOnClickListener(object : View.OnClickListener {
            private var lastClickTime: Long = 0

            override fun onClick(v: View?) {
                val clickTime = System.currentTimeMillis()
                if (clickTime - lastClickTime < 300) {
                    showEditDeleteDialog(position)
                }
                lastClickTime = clickTime
            }
        })

        return view
    }

    // Method to show the Edit/Delete dialog
    private fun showEditDeleteDialog(position: Int) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.task_dialog, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        // Handle Edit Task button click
        dialogView.findViewById<Button>(R.id.editTaskButton).setOnClickListener {
            // Here, implement the task editing logic (e.g., show a dialog to modify task)
            dialog.dismiss()
        }

        // Handle Delete Task button click
        dialogView.findViewById<Button>(R.id.deleteTaskButton).setOnClickListener {
            taskList.removeAt(position)
            notifyDataSetChanged()
            dialog.dismiss()
        }

        dialog.show()
    }
}