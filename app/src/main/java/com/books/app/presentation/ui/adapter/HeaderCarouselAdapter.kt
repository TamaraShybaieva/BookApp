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

class HeaderCarouselAdapter(
        private val books: List<Book>,
        private val onBookClick: ((Book) -> Unit)?
) : RecyclerView.Adapter<HeaderCarouselAdapter.CarouselViewHolder>() {

    class CarouselViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coverImage: ImageView = view.findViewById(R.id.book_cover)
        val titleTextView = view.findViewById<TextView>(R.id.book_title)
        val authorTextView = view.findViewById<TextView>(R.id.author_title)

        fun bind(bannerSlide: Book) {
            Glide.with(coverImage.context)
                    .load(bannerSlide.coverUrl)
                    .into(coverImage)
            titleTextView.text = bannerSlide.name
            authorTextView.text = bannerSlide.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_carousel_book, parent, false)
        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)

        holder.itemView.setOnClickListener {
            onBookClick?.invoke(book)
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }
}