package com.lucassdalmeida.araucaria.model

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.lucassdalmeida.araucaria.R
import java.sql.SQLException

class ContactSQLiteDAO(context: Context) : ContactDAO {
    companion object Constant {
        private const val CONTACT_DATABASE_FILE = "contacts"
        private const val CONTACT_TABLE = "contact"
        private const val ID_COLUMN = "id"
        private const val NAME_COLUMN = "name"
        private const val ADDRESS_COLUMN = "address"
        private const val PHONE_COLUMN = "phone"
        private const val EMAIL_COLUMN = "email"
        private const val CREATE_CONTACT_TABLE_STATEMENT = """
            CREATE TABLE IF NOT EXISTS $CONTACT_TABLE(
                $ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT,
                $NAME_COLUMN TEXT NOT NULL,
                $ADDRESS_COLUMN TEXT NOT NULL,
                $PHONE_COLUMN TEXT NOT NULL,
                $EMAIL_COLUMN TEXT NOT NULL
            );
        """
    }

    private val contactsSqliteDatabase: SQLiteDatabase

    init {
        contactsSqliteDatabase = context.openOrCreateDatabase(CONTACT_DATABASE_FILE, MODE_PRIVATE,
                                        null)

        try {
            contactsSqliteDatabase.execSQL(CREATE_CONTACT_TABLE_STATEMENT)
        }
        catch (error: SQLException) {
            Log.e(context.getString(R.string.app_name), error.message.toString())
        }
    }

    override fun createContact(contact: Contact): Int {
        val cv = contact.toContentValues()
        return contactsSqliteDatabase.insert(CONTACT_TABLE, null, cv).toInt()
    }

    override fun retrieveContact(id: Int): Contact {
        TODO("Not yet implemented")
    }

    override fun retrieveContacts(): List<Contact> {
        TODO("Not yet implemented")
    }

    override fun updateContact(contact: Contact) = contactsSqliteDatabase.update(
        CONTACT_TABLE,
        contact.toContentValues(),
        "$ID_COLUMN = ?",
        arrayOf(contact.id.toString())
    )

    override fun deleteContact(id: Int) = contactsSqliteDatabase.delete(
        CONTACT_TABLE,
        "$ID_COLUMN = ?",
        arrayOf(id.toString())
    )

    private fun Contact.toContentValues() = ContentValues().apply {
        put(NAME_COLUMN, name)
        put(EMAIL_COLUMN, email)
        put(PHONE_COLUMN, phone)
        put(ADDRESS_COLUMN, address)
    }
}