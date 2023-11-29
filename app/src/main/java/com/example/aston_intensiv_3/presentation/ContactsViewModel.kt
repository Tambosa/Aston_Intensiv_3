package com.example.aston_intensiv_3.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.aston_intensiv_3.domain.ContactEntity
import com.example.aston_intensiv_3.domain.ContactsRecyclerItem
import com.example.aston_intensiv_3.domain.ContactsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(private val repo: ContactsRepo) : ViewModel() {
    private val _state = MutableLiveData<MutableList<ContactsRecyclerItem>>()
    val state: LiveData<List<ContactsRecyclerItem>> = _state.map {
        it.toList()
    }

    fun getState() {
        viewModelScope.launch {
            if (_state.value == null) {
                _state.value = mutableListOf<ContactsRecyclerItem>().apply {
                    addAll(repo.getContacts())
                }
            }
        }
    }

    fun addContact(contact: ContactEntity) {
        viewModelScope.launch {
            _state.value = _state.value?.apply {
                add(contact)
                sortBy { (it as ContactEntity).id }
            }
        }
    }

    fun deleteContact(contact: ContactEntity) {
        viewModelScope.launch {
            _state.value = _state.value?.apply {
                remove(contact)
                sortBy { (it as ContactEntity).id }
            }
        }
    }

    fun updateContact(newContact: ContactEntity) {
        viewModelScope.launch {
            val oldContact = _state.value?.find { (it as ContactEntity).id == newContact.id }
            _state.value = _state.value?.apply {
                remove(oldContact)
                add(newContact)
                sortBy { (it as ContactEntity).id }
            }
        }
    }
}