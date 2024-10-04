package com.example.bottomnavTagli

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


class ProfileFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        val nameEditText = view.findViewById<EditText>(R.id.nameEditText)
        val addressEditText = view.findViewById<EditText>(R.id.addressEditText)
        val genderRadioGroup = view.findViewById<RadioGroup>(R.id.genderRadioGroup)
        val sportsCheckBox = view.findViewById<CheckBox>(R.id.sportsCheckBox)
        val gamingCheckBox = view.findViewById<CheckBox>(R.id.gamingCheckBox)
        val readingCheckBox = view.findViewById<CheckBox>(R.id.readingCheckBox)
        val musicCheckBox = view.findViewById<CheckBox>(R.id.musicCheckBox)
        val adventureCheckBox = view.findViewById<CheckBox>(R.id.adventureCheckBox)
        val moviesCheckBox = view.findViewById<CheckBox>(R.id.moviesCheckBox)
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        sharedViewModel.profileData.observe(viewLifecycleOwner) { profile ->
            nameEditText.setText(profile.name)
            addressEditText.setText(profile.address)
            when (profile.gender) {
                "Male" -> genderRadioGroup.check(R.id.maleRadioButton)
                "Female" -> genderRadioGroup.check(R.id.femaleRadioButton)
            }
            sportsCheckBox.isChecked = profile.hobbies.contains("Sports")
            gamingCheckBox.isChecked = profile.hobbies.contains("Gaming")
            readingCheckBox.isChecked = profile.hobbies.contains("Reading")
            musicCheckBox.isChecked = profile.hobbies.contains("Music")
            adventureCheckBox.isChecked = profile.hobbies.contains("Adventure")
            moviesCheckBox.isChecked = profile.hobbies.contains("Movies")
        }

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val address = addressEditText.text.toString()
            val selectedGenderId = genderRadioGroup.checkedRadioButtonId
            val gender = view.findViewById<RadioButton>(selectedGenderId)?.text.toString()

            val hobbies = mutableListOf<String>()
            if (sportsCheckBox.isChecked) hobbies.add("Sports")
            if (gamingCheckBox.isChecked) hobbies.add("Gaming")
            if (readingCheckBox.isChecked) hobbies.add("Reading")
            if (musicCheckBox.isChecked) hobbies.add("Music")
            if (adventureCheckBox.isChecked) hobbies.add("Adventure")
            if (moviesCheckBox.isChecked) hobbies.add("Movies")

            sharedViewModel.saveProfileData(Profile(name, address, gender, hobbies))
        }
    }
}

