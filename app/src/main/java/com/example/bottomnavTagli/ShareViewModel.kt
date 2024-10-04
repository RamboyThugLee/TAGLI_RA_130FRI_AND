package com.example.bottomnavTagli

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private val _listItems = MutableLiveData<MutableList<ListItem>>(mutableListOf())
    val listItems: LiveData<MutableList<ListItem>> = _listItems
    val calculatorResult = MutableLiveData<Double>()

    fun addItem(item: ListItem) {
        _listItems.value?.add(item)
        _listItems.value = _listItems.value // Notify observers
    }

    private val _profileData = MutableLiveData<Profile>()
    val profileData: LiveData<Profile> = _profileData

    fun saveProfileData(profile: Profile) {
        _profileData.value = profile
    }
}

data class Profile(
    val name: String,
    val address: String,
    val gender: String,
    val hobbies: List<String>
)
