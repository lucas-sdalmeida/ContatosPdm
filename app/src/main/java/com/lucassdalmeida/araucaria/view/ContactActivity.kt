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

        val saveBt = contactActivityViewBinding.saveBt

        with(contactActivityViewBinding) {
            saveBt.setOnClickListener {
                val name = nameEt.toString()
                val address = addressEt.toString()
                val phone = phoneEt.toString()
                val email = emailEt.toString()

                val contact = Contact(generateId(), name, address, phone, email)

                val resultIntent = Intent()
                resultIntent.putExtra(Constants.EXTRA_CONTACT, contact)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun generateId() = Random(System.currentTimeMillis()).nextInt()
}