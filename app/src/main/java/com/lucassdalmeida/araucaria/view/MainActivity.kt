package com.lucassdalmeida.araucaria.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.lucassdalmeida.araucaria.R
import com.lucassdalmeida.araucaria.adapter.ContactAdapter
import com.lucassdalmeida.araucaria.databinding.ActivityMainBinding
import com.lucassdalmeida.araucaria.model.Constants.EXTRA_CONTACT
import com.lucassdalmeida.araucaria.model.Contact

class MainActivity : AppCompatActivity() {
    private val activityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val contactAdapter by lazy {
        ContactAdapter(this, contactList)
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
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val contact = result.data?.getParcelableExtra<Contact>(EXTRA_CONTACT)
                contact?.let { c ->
                    if (contactList.any { item -> item.id == c.id }) {
                        val position = contactList.indexOfFirst { it.id == c.id }
                        contactList[position] = c
                    }
                    else {
                        contactList.add(c)
                    }
                    contactAdapter.notifyDataSetChanged()
                }
            }
        }

        registerForContextMenu(activityMainBinding.contactsListView)
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

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?,
                                     menuInfo: ContextMenu.ContextMenuInfo?) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterContextMenuInfo).position

        return when(item.itemId) {
            R.id.removeContactMenuItem -> {
                contactList.removeAt(position)
                contactAdapter.notifyDataSetChanged()

                Toast.makeText(this, "Contact removed", Toast.LENGTH_SHORT).show()

                true
            }
            R.id.editContactMenuItem -> {
                val contact = contactList[position]
                val editContactIntent = Intent(this, ContactActivity::class.java)

                editContactIntent.putExtra(EXTRA_CONTACT, contact)
                contactActivityResultLauncher.launch(editContactIntent)

                true
            }
            else -> {
                false
            }
        }
    }

    private fun fillContacts() {
        for (i  in 0..50)
            contactList.add(Contact(i, "Name $i", "Address $i", "Phone $i",
                "Email $i"))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterForContextMenu(activityMainBinding.contactsListView)
    }
}