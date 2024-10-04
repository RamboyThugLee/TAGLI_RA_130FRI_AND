package com.example.bottomnavTagli

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment

class ListFragment : Fragment() {

    private lateinit var taskAdapter: CustomAdapter
    private val taskList = ArrayList<String>()
    private var lastClickTime: Long = 0
    private val doubleClickThreshold: Long = 300

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val listView = view.findViewById<ListView>(R.id.listView)
        val addButton = view.findViewById<Button>(R.id.addTaskButton)
        val editText = view.findViewById<EditText>(R.id.editTextTask)

        taskAdapter = CustomAdapter(requireContext(), taskList)
        listView.adapter = taskAdapter

        val scaleUp = AnimationUtils.loadAnimation(requireContext(), R.anim.scale_up)

        addButton.setOnClickListener {
            val task = editText.text.toString()

            if (task.isNotEmpty()) {

                taskList.add(task)
                taskAdapter.notifyDataSetChanged()
                editText.text.clear()

                listView.startAnimation(scaleUp)
            }
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val currentClickTime = System.currentTimeMillis()
            val clickInterval = currentClickTime - lastClickTime

            if (clickInterval < doubleClickThreshold) {

                taskAdapter.showTaskOptionsDialog(position)
            }

            lastClickTime = currentClickTime
        }

        return view
    }
}
