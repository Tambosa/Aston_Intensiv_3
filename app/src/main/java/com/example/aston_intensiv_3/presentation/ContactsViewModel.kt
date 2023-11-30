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
                    sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { (it as ContactEntity).name })
                }
            }
        }
    }

    fun addContact(contact: ContactEntity) {
        viewModelScope.launch {
            _state.value = _state.value?.apply {
                add(contact.copy(id = getUniqueId()))
                sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { (it as ContactEntity).name })
            }
        }
    }

    private fun getUniqueId(): Int {
        var id = 0
        try {
            id = (_state.value?.maxWith(Comparator.comparingInt {
                (it as ContactEntity).id
            }) as ContactEntity).id
        } catch (e: Exception) {
            return id
        }
        return (id + 1)
    }

    fun deleteContact(contact: ContactEntity) {
        viewModelScope.launch {
            _state.value = _state.value?.apply {
                remove(contact)
                sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { (it as ContactEntity).name })
            }
        }
    }

    fun updateContact(newContact: ContactEntity) {
        viewModelScope.launch {
            val oldContact = _state.value?.find { (it as ContactEntity).id == newContact.id }
            _state.value = _state.value?.apply {
                remove(oldContact)
                add(newContact)
                sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { (it as ContactEntity).name })
            }
        }
    }

    fun setContactSelected(contact: ContactEntity) {
        viewModelScope.launch {
            _state.value = _state.value?.apply {
                remove(contact)
                add(contact.copy(isSelected = !contact.isSelected))
                sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { (it as ContactEntity).name })
            }
        }
    }

    fun deleteAllSelected() {
        viewModelScope.launch {
            val newList = mutableListOf<ContactsRecyclerItem>()
            _state.value?.forEach {
                if (!(it as ContactEntity).isSelected) {
                    newList.add(it)
                }
            }
            _state.value = newList
        }
    }

    fun undoAllSelected() {
        viewModelScope.launch {
            val newList = mutableListOf<ContactsRecyclerItem>()
            _state.value?.forEach {
                newList.add((it as ContactEntity).copy(isSelected = false))
            }
            _state.value = newList
        }
    }
}