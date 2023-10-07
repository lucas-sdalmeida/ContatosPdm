package com.lucassdalmeida.araucaria.model

interface ContactDAO {
    fun createContact(contact: Contact) : Int

    fun retrieveContact(id: Int) : Contact

    fun retrieveContacts() : List<Contact>

    fun updateContact(contact: Contact) : Int

    fun deleteContact(id: Int) : Int
}