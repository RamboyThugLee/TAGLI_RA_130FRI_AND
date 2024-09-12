package com.ucb.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private val taskList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listView)
        val addButton = findViewById<Button>(R.id.addTaskButton)
        val editText = findViewById<EditText>(R.id.editTextTask)

        // Initialize the TaskAdapter with the taskList
        taskAdapter = TaskAdapter(this, taskList)
        listView.adapter = taskAdapter

        // Load the animation resource
        val scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)

        // Add a task to the list when the button is clicked
        addButton.setOnClickListener {
            val task = editText.text.toString()

            if (task.isNotEmpty()) {
                // Add task to the list
                taskList.add(task)
                taskAdapter.notifyDataSetChanged()
                editText.text.clear()

                // Start the scale animation on the ListView
                listView.startAnimation(scaleUp)
            }
        }

        // Handling item clicks in the ListView (double click for edit/delete)
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedTask = taskList[position]
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Manage Task")
                .setMessage("What would you like to do with this task?")
                .setPositiveButton("Edit") { _, _ ->
                    // Show an EditText dialog to edit the task
                    val editTaskDialog = EditText(this)
                    editTaskDialog.setText(selectedTask)
                    AlertDialog.Builder(this)
                        .setTitle("Edit Task")
                        .setView(editTaskDialog)
                        .setPositiveButton("Save") { _, _ ->
                            taskList[position] = editTaskDialog.text.toString()
                            taskAdapter.notifyDataSetChanged()
                        }
                        .setNegativeButton("Cancel", null)
                        .show()
                }
                .setNegativeButton("Delete") { _, _ ->
                    // Delete the selected task
                    taskList.removeAt(position)
                    taskAdapter.notifyDataSetChanged()
                }
                .setNeutralButton("Cancel", null)
                .show()
        }
    }

    // Custom adapter for ListView (including ImageView and CheckBox)
    private class TaskAdapter(context: MainActivity, private val tasks: ArrayList<String>) :
        ArrayAdapter<String>(context, R.layout.task_item, tasks) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater = LayoutInflater.from(context) // Fix for layoutInflater
            val rowView = inflater.inflate(R.layout.task_item, parent, false)

            val taskText = rowView.findViewById<TextView>(R.id.taskText)
            val checkBox = rowView.findViewById<CheckBox>(R.id.taskCheckBox)
            val imageView = rowView.findViewById<ImageView>(R.id.taskImage)

            taskText.text = tasks[position]

            // Mark task as completed/uncompleted when checkbox is clicked
            checkBox.setOnCheckedChangeListener { _, isChecked ->  // Fix for isChecked
                if (isChecked) {
                    taskText.paintFlags = taskText.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG // Fix for paintFlags
                } else {
                    taskText.paintFlags = taskText.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }

            return rowView
        }
    }
}