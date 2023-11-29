package com.example.aston_intensiv_3.data

import com.example.aston_intensiv_3.domain.ContactEntity
import com.example.aston_intensiv_3.domain.ContactsRepo

class ContactsRepoImpl : ContactsRepo {
    override fun getContacts() = listOf<ContactEntity>(
        ContactEntity(id = 1, name = "Andrei", surname = "Smith", phoneNumber = 123001),
        ContactEntity(id = 2, name = "Feliks", surname = "Walters", phoneNumber = 123002),
        ContactEntity(id = 3, name = "Yulian", surname = "Wolf", phoneNumber = 123003),
        ContactEntity(id = 4, name = "Dmitriy", surname = "Morgan", phoneNumber = 123004),
        ContactEntity(id = 5, name = "Alexandr", surname = "Adams", phoneNumber = 123005),
        ContactEntity(id = 6, name = "Vsevolod", surname = "Reese", phoneNumber = 123006),
        ContactEntity(id = 7, name = "Ignat", surname = "Daniels", phoneNumber = 123007),
        ContactEntity(id = 8, name = "Matvei", surname = "Torres", phoneNumber = 123008),
        ContactEntity(id = 9, name = "Vasiliy", surname = "Reyes", phoneNumber = 123009),
        ContactEntity(id = 10, name = "Abram", surname = "Chapman", phoneNumber = 123010),
    )
}