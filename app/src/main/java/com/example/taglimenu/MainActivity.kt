package com.example.taglimenu

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
    }

    // Set up the toolbar with a title and subtitle
    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "Tagli Menu"
            subtitle = "9/20/2024"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_first -> {
                loadFragment(AnotherFragment())
                true
            }
            R.id.menu_second -> {
                MyDialog().show(supportFragmentManager, MyDialog.TAG)
                true
            }
            R.id.submenu_exit -> {
                // Handle exit submenu selection
                true
            }
            R.id.exit_confirmation -> {
                finish() // Close the activity
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Load a fragment and add it to the back stack
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    // Custom dialog fragment
    class MyDialog : DialogFragment() {
        companion object {
            const val TAG = "MyDialog"
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
            return AlertDialog.Builder(requireActivity())
                .setTitle("Congratulations!")
                .setMessage("If you can see this you are amazing!")
                .setPositiveButton("OK", null) // No action needed on click
                .setNegativeButton("Cancel", null) // No action needed on click
                .create()
        }
    }
}
