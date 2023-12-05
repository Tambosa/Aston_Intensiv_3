package com.example.aston_intensiv_3.presentation.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.aston_intensiv_3.databinding.ContactItemBinding
import com.example.aston_intensiv_3.domain.ContactEntity
import com.example.aston_intensiv_3.domain.ContactsRecyclerItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun contactAdapterDelegate(
    onItemClick: (item: ContactEntity) -> Unit,
    onItemLongClick: (item: ContactEntity) -> Boolean,
    onDeleteClick: (item: ContactEntity) -> Unit,
) =
    adapterDelegateViewBinding<ContactEntity, ContactsRecyclerItem, ContactItemBinding>({ layoutInflater, root ->
        ContactItemBinding.inflate(layoutInflater, root, false)
    }) {
        binding.root.setOnClickListener { onItemClick(item) }
        binding.root.setOnLongClickListener { onItemLongClick(item) }
        binding.contactItemBtnDelete.setOnClickListener { onDeleteClick(item) }

        bind {
            if (item.isSelected) {
                binding.root.background = ColorDrawable(Color.RED)
            } else {
                binding.root.background = ColorDrawable(Color.TRANSPARENT)
            }
            binding.contactItemName.text = item.name
            binding.contactItemSurname.text = item.surname
            binding.contactItemNumber.text = item.phoneNumber.toString()
        }
    }