package com.ucb.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tasklist.TaskAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private val taskList = ArrayList<String>()
    private var lastClickTime: Long = 0
    private val doubleClickThreshold: Long = 300 // Threshold in milliseconds for double-click

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

        // Handling item clicks in the ListView (double-click for edit/delete)
        listView.setOnItemClickListener { _, view, position, _ ->
            val currentClickTime = System.currentTimeMillis()
            val clickInterval = currentClickTime - lastClickTime

            if (clickInterval < doubleClickThreshold) {
                // Double click detected
                taskAdapter.showTaskOptionsDialog(position)
            }

            lastClickTime = currentClickTime
        }
    }
}