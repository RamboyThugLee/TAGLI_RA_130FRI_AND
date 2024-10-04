package com.example.taglimenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class AnotherFragment : Fragment() {

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Using non-null assertion as we assume the layout is always valid
        return inflater.inflate(R.layout.fragment_another, container, false)
    }
}