package com.example.bottomnavTagli

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navigationBar: BottomNavigationView
    private var activeTabId: Int = R.id.nav_list // Set default tab to List

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Restore active tab on orientation change or instance restore
        savedInstanceState?.let {
            activeTabId = it.getInt("activeTabId", R.id.nav_list)
        }

        navigationBar = findViewById(R.id.bottomNavigationView)

        // Load the fragment corresponding to the active tab
        switchToFragment(getFragmentForTab(activeTabId))

        // Configure the BottomNavigationView click listener
        navigationBar.setOnItemSelectedListener { menuItem ->
            activeTabId = menuItem.itemId
            switchToFragment(getFragmentForTab(activeTabId))
            true
        }

        // Ensure the correct tab is selected upon orientation change
        navigationBar.selectedItemId = activeTabId
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("activeTabId", activeTabId) // Save active tab ID
    }

    private fun getFragmentForTab(tabId: Int): Fragment {
        return when (tabId) {
            R.id.nav_calculator -> CalculatorFragment()
            R.id.nav_list -> ListFragment()
            R.id.nav_profile -> ProfileFragment()
            else -> ListFragment() // Fallback to ListFragment as default
        }
    }

    private fun switchToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
