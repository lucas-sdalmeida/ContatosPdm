package com.lucassdalmeida.araucaria.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id : Int,
    val name : String,
    val address : String,
    val phone : String,
    val email : String,
) : Parcelable