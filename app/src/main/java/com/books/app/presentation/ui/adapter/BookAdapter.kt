package com.books.app.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.books.app.R
import com.books.app.model.Book
import com.bumptech.glide.Glide

class BookAdapter(var books: List<Book>, val isPrimaryTextColor: Boolean, val clickListener: (Book) -> Unit)  :
        RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int = books.size

    inner class BookViewHolder(val container: View) : RecyclerView.ViewHolder(container) {
        private val text = itemView.findViewById<TextView>(R.id.textView4)
        private val imageView = itemView.findViewById<ImageView>(R.id.imageView2)
        fun bind(bannerSlide: Book) {
            val maxChars = 15
            val limitedText = if (bannerSlide.name.length > maxChars) {
                bannerSlide.name.substring(0, maxChars) + "..."
            } else {
                bannerSlide.name
            }

            text.text = limitedText
            if (isPrimaryTextColor) {
                text.setTextColor(text.context.getColor(R.color.primary_text_color))
            } else {
                text.setTextColor(text.context.getColor(R.color.white))
            }
            Glide.with(container.context)
                    .load(  bannerSlide.coverUrl)
                    .into(imageView)
            itemView.setOnClickListener {
                clickListener(bannerSlide)
            }
        }
    }
}