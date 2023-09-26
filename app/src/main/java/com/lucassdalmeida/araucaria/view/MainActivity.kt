package com.lucassdalmeida.araucaria.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.lucassdalmeida.araucaria.R
import com.lucassdalmeida.araucaria.databinding.ActivityMainBinding
import com.lucassdalmeida.araucaria.model.Constants.EXTRA_CONTACT
import com.lucassdalmeida.araucaria.model.Contact

// ContatosPdm

class MainActivity : AppCompatActivity() {
    private val activityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val contactAdapter by lazy {
        ArrayAdapter(this, android.R.layout.simple_list_item_1,
            contactList.map { it.name })
    }
    private val contactList : MutableList<Contact> = mutableListOf()
    private lateinit var contactActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)
        setSupportActionBar(activityMainBinding.toolbarIn.toolbar)

        val contactsListView = activityMainBinding.contactsListView
        fillContacts()
        contactsListView.adapter = contactAdapter

        contactActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {result ->
            if (result.resultCode == RESULT_OK) {
                val contact = result.data?.getParcelableExtra<Contact>(EXTRA_CONTACT)
                contact?.let {
                    contactList.add(it)
                    contactAdapter.add(it.name)
                    contactAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.add_contact_menu_item -> {
                contactActivityResultLauncher.launch(Intent(this,
                    ContactActivity::class.java))
                true
            }
            else -> false
        }
    }

    private fun fillContacts() {
        for (i  in 0..50)
            contactList.add(Contact(i, "Name $i", "Address $i", "Phone $i",
                "Email $i"))
    }
}