package com.example.aston_intensiv_3.domain

sealed interface ContactsRecyclerItem

interface ContactItem : ContactsRecyclerItem
interface AddContactItem : ContactsRecyclerItem