package com.example.bottomnavTagli

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class ListFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var inputField: EditText
    private lateinit var addButton: Button
    private lateinit var listAdapter: CustomAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView = view.findViewById(R.id.listView)
        inputField = view.findViewById(R.id.editText)
        addButton = view.findViewById(R.id.addButton)

        // Check if the list is empty and add default items if necessary
        if (sharedViewModel.listItems.value.isNullOrEmpty()) {
            // Populate initial list with default items if it's empty
            sharedViewModel.addItem(ListItem(ItemType.CHECKBOX, "Sample Checkbox Item", isChecked = true))
            sharedViewModel.addItem(ListItem(ItemType.TEXTVIEW, "Sample Text Item"))
            sharedViewModel.addItem(ListItem(ItemType.IMAGEVIEW, "Sample Image Item"))
        }

        listAdapter = CustomAdapter(requireContext(), sharedViewModel.listItems.value ?: mutableListOf())
        listView.adapter = listAdapter

        addButton.setOnClickListener {
            val userInput = inputField.text.toString()
            if (userInput.isNotBlank()) {
                // Add new item to the list based on user input
                val newItem = ListItem(ItemType.TEXTVIEW, text = userInput)
                sharedViewModel.addItem(newItem)
                listAdapter.notifyDataSetChanged()
                inputField.text.clear()
            }
        }

        // Observe changes in the list and update the adapter
        sharedViewModel.listItems.observe(viewLifecycleOwner) { newItems ->
            listAdapter.notifyDataSetChanged()
        }
    }
}
