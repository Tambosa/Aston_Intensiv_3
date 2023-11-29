package com.example.aston_intensiv_3.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aston_intensiv_3.Utils
import com.example.aston_intensiv_3.databinding.ActivityContactsBinding
import com.example.aston_intensiv_3.domain.AddContactItemSingleton
import com.example.aston_intensiv_3.domain.ContactEntity
import com.example.aston_intensiv_3.domain.ContactsRecyclerItem
import com.example.aston_intensiv_3.presentation.adapters.ContactsCompositeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactsBinding
    private lateinit var viewModel: ContactsViewModel
    private val contactAdapter = ContactsCompositeAdapter(
        onContactItemClick = { item -> updateContact(item) },
        onContactDeleteClick = { item -> deleteContact(item) },
        onContactItemLongClick = { item -> onContactItemLongClick(item) },
        onAddContactClick = { addContact() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ContactsViewModel::class.java]
        initViewModel()
        initRecycler()
    }

    private fun initViewModel() {
        viewModel.state.observe(this) { state ->
            val data = mutableListOf<ContactsRecyclerItem>().apply {
                addAll(state)
                add(AddContactItemSingleton)
            }
            renderRecyclerData(data.toList())
            renderFabs(state.map { it as ContactEntity })
        }
        viewModel.getState()
    }

    private fun renderRecyclerData(newList: List<ContactsRecyclerItem>) {
        val oldList = contactAdapter.items ?: listOf()
        val diff = Utils.getStandardDiff(oldList, newList)
        contactAdapter.items = newList
        diff.dispatchUpdatesTo(contactAdapter)
    }

    private fun renderFabs(state: List<ContactEntity>) {
        var isSelection = false
        state.forEach {
            if (it.isSelected) {
                isSelection = true
            }
        }
        binding.btnDeleteAll.visibility = if (isSelection) View.VISIBLE else View.GONE
        binding.btnUndoSelection.visibility = if (isSelection) View.VISIBLE else View.GONE
        binding.btnDeleteAll.setOnClickListener { viewModel.deleteAllSelected() }
        binding.btnUndoSelection.setOnClickListener { viewModel.undoAllSelected() }
    }


    private fun initRecycler() {
        binding.contactRecyclerView.apply {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
        }
    }

    private fun deleteContact(contact: ContactEntity) {
        viewModel.deleteContact(contact)
    }

    private fun updateContact(contact: ContactEntity) {

    }

    private fun addContact() {
    }

    private fun onContactItemLongClick(item: ContactEntity): Boolean {
        viewModel.setContactSelected(item)
        return !item.isSelected
    }
}