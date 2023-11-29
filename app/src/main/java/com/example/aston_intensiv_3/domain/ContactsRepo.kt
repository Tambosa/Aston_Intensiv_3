package com.example.aston_intensiv_3.domain

interface ContactsRepo {
    fun getContacts(): List<ContactEntity>
}