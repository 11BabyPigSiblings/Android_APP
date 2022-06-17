package com.goni99.smartlibrarysystem.model

data class RentBook(
    val id: Int,
    val isbn13: String?,
    val title: String?,
    val author: String?,
    val publisher: String?,
    val imgUrl: String?,
    val kdc: String?
)
