package com.example.tagli_headlinenews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tagli_headlinenews.R

class MainActivity : AppCompatActivity() {

    private var isTabletLayout: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "Activity created")
        setContentView(R.layout.activity_main)

        isTabletLayout = findViewById<View?>(R.id.detailContainer) != null
        Log.d("MainActivity", "Tablet mode: $isTabletLayout")

        if (savedInstanceState == null) {
            Log.d("MainActivity", "Loading default fragments")
            initializeFragments()
        }
    }

    private fun initializeFragments() {
        val newsFragment = NewsListFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.headlineContainer, newsFragment)
            if (isTabletLayout) {
                val defaultDetailFragment = NewsDetailFragment.newInstance(
                    "Select a news headline", "Content will be shown here."
                )
                replace(R.id.detailContainer, defaultDetailFragment)
            }
            commit()
        }
    }

    fun displayDetails(title: String, description: String) {
        if (isTabletLayout) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.detailContainer, NewsDetailFragment.newInstance(title, description))
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                // In phone mode, the `headlineContainer` is swapped with `NewsDetailFragment`
                replace(R.id.headlineContainer, NewsDetailFragment.newInstance(title, description))
                addToBackStack(null)
                commit()
            }
        }
    }
}

class NewsListFragment : Fragment() {

    private val headlines = listOf(
        "Community Spotlight: Local Heroes",
        "Innovation Update: Smart Home Devices",
        "Global Insight: Climate Action Strategies",
        "Cultural Buzz: Art Festival Highlights",
        "Sports Roundup: Championship Highlights"
    )

    private val descriptions = listOf(
        "Celebrating local heroes who made a difference in the community.",
        "Exploring the latest innovations in smart home technology.",
        "Discussing effective strategies for climate action globally.",
        "Highlights from the recent art festival showcasing local talent.",
        "Key moments and scores from the championship games."
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_news_list, container, false)
        val listView = rootView.findViewById<ListView>(R.id.newsListView)

        listView.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, headlines)
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val parentActivity = requireActivity() as MainActivity
            parentActivity.displayDetails(headlines[position], descriptions[position])
        }

        return rootView
    }
}

class NewsDetailFragment : Fragment() {

    private var title: String? = null
    private var content: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            content = it.getString(ARG_CONTENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_news_details, container, false)
        rootView.findViewById<TextView>(R.id.headlineTextView).text = title
        rootView.findViewById<TextView>(R.id.contentTextView).text = content
        return rootView
    }

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_CONTENT = "content"

        @JvmStatic
        fun newInstance(title: String, content: String) =
            NewsDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_CONTENT, content)
                }
            }
    }
}