package com.example.aston_intensiv_3.presentation.adapters

import com.example.aston_intensiv_3.domain.ContactEntity
import com.example.aston_intensiv_3.domain.ContactsRecyclerItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class ContactsCompositeAdapter(
    onContactItemClick: (item: ContactEntity) -> Unit,
    onContactItemLongClick: (item: ContactEntity) -> Boolean,
    onContactDeleteClick: (item: ContactEntity) -> Unit,
    onAddContactClick: () -> Unit
) : ListDelegationAdapter<List<ContactsRecyclerItem>>(
    contactAdapterDelegate(
        onItemClick = onContactItemClick,
        onItemLongClick = onContactItemLongClick,
        onDeleteClick = onContactDeleteClick
    ),
    addContactAdapterDelegate(
        onAddContact = onAddContactClick
    )
)