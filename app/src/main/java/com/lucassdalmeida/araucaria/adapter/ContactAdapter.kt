package com.lucassdalmeida.araucaria.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.lucassdalmeida.araucaria.R
import com.lucassdalmeida.araucaria.databinding.TileContactBinding
import com.lucassdalmeida.araucaria.model.Contact

class ContactAdapter(
    context: Context,
    private val contactList: MutableList<Contact>,
) : ArrayAdapter<Contact>(context, LAYOUT, contactList) {
    companion object {
        val LAYOUT = R.layout.tile_contact
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contact = contactList[position]
        val targetView = convertView ?: createNewTile(parent)
        val holder = targetView.tag as TileContactHolder

        holder.nameTextView.text = contact.name
        holder.emailTextView.text = contact.email

        return targetView
    }

    private fun createNewTile(parent : ViewGroup) : View {
        val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val tileContactBinding = TileContactBinding.inflate(inflater, parent, false)
        val tileContactHolder = TileContactHolder(tileContactBinding.nameTextView,
                                                tileContactBinding.emailTextView)
        val view = tileContactBinding.root
        view.tag = tileContactHolder

        return view
    }

    private data class TileContactHolder(val nameTextView: TextView, val emailTextView: TextView)
}