package com.books.app.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.books.app.R
import com.books.app.model.Book
import com.books.app.model.Genre

class GenreAdapter(private val clickListener: (Book) -> Unit) :
        RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    private val genres = mutableListOf<Genre>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Genre>) {
        genres.clear()
        genres.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount(): Int = genres.size

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val text = itemView.findViewById<TextView>(R.id.textView3)
        private val recyclerView = itemView.findViewById<RecyclerView>(R.id.genre_recycler)
        fun bind(genre: Genre) {
            text.text = genre.genre

            val adapter = BookAdapter(genre.books, false) { bookId ->
                clickListener(bookId)
            }
            recyclerView.apply {
                layoutManager =  LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
            }
            recyclerView.adapter = adapter
        }
    }
}
