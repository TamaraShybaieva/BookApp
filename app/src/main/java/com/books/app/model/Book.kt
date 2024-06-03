package com.books.app.model

import java.io.Serializable

data class Book(
        val id: Int,
        val name: String,
        val author: String,
        val summary: String,
        val genre: String,
        val coverUrl: String,
        val views: String,
        val likes: String,
        val quotes: String
) : Serializable