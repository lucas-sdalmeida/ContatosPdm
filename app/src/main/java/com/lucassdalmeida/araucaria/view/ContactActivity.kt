package com.lucassdalmeida.araucaria.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lucassdalmeida.araucaria.databinding.ActivityContactBinding
import com.lucassdalmeida.araucaria.model.Constants
import com.lucassdalmeida.araucaria.model.Contact
import kotlin.random.Random

class ContactActivity : AppCompatActivity() {
    private val contactActivityViewBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contactActivityViewBinding.root)
        setSupportActionBar(contactActivityViewBinding.toolbarIn.toolbar)

        supportActionBar?.let{ it.subtitle = "Contact details" }
        val saveBt = contactActivityViewBinding.saveBt

        val receivedContact = intent.getParcelableExtra<Contact>(Constants.EXTRA_CONTACT)

        receivedContact?.let {
            with(contactActivityViewBinding) {
                nameEt.setText(it.name)
                addressEt.setText(it.address)
                phoneEt.setText(it.phone)
                emailEt.setText(it.email)
            }
        }

        with(contactActivityViewBinding) {
            saveBt.setOnClickListener {
                val name = nameEt.text.toString()
                val address = addressEt.text.toString()
                val phone = phoneEt.text.toString()
                val email = emailEt.text.toString()
                val id = receivedContact?.id ?: generateId()

                val contact = Contact(id, name, address, phone, email)

                val resultIntent = Intent()
                resultIntent.putExtra(Constants.EXTRA_CONTACT, contact)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun generateId() = Random(System.currentTimeMillis()).nextInt()
}