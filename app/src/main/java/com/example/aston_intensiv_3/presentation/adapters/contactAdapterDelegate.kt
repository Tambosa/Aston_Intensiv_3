package com.example.aston_intensiv_3.presentation.adapters

import com.example.aston_intensiv_3.databinding.ContactItemBinding
import com.example.aston_intensiv_3.domain.ContactEntity
import com.example.aston_intensiv_3.domain.ContactsRecyclerItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun contactAdapterDelegate(
    onItemClick: (item: ContactEntity) -> Unit,
    onDeleteClick: (item: ContactEntity) -> Unit,
) =
    adapterDelegateViewBinding<ContactEntity, ContactsRecyclerItem, ContactItemBinding>({ layoutInflater, root ->
        ContactItemBinding.inflate(layoutInflater, root, false)
    }) {
        binding.root.setOnClickListener { onItemClick(item) }
        binding.contactItemBtnDelete.setOnClickListener { onDeleteClick(item) }
        bind {
            binding.contactItemName.text = item.name
            binding.contactItemSurname.text = item.surname
            binding.contactItemNumber.text = item.phoneNumber.toString()
        }
    }