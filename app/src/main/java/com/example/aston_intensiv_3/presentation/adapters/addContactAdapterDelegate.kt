package com.example.aston_intensiv_3.presentation.adapters

import com.example.aston_intensiv_3.databinding.AddContactItemBinding
import com.example.aston_intensiv_3.domain.AddContactItemSingleton
import com.example.aston_intensiv_3.domain.ContactsRecyclerItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun addContactAdapterDelegate(
    onAddContact: () -> Unit,
) =
    adapterDelegateViewBinding<AddContactItemSingleton, ContactsRecyclerItem, AddContactItemBinding>(
        { layoutInflater, root ->
            AddContactItemBinding.inflate(layoutInflater, root, false)
        }) {
        binding.addNewContactBtn.setOnClickListener { onAddContact() }
    }