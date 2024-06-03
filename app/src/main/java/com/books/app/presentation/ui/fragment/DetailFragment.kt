package com.books.app.presentation.ui.fragment

import DetailsViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.books.app.R
import com.books.app.model.Book
import com.books.app.presentation.ui.adapter.BookAdapter
import com.books.app.presentation.ui.adapter.HeaderCarouselAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment: Fragment() {

    private val viewModel by viewModel<DetailsViewModel>()
    private lateinit var headerRecyclerView: RecyclerView
    private lateinit var recommendedRecyclerView: RecyclerView
    private lateinit var bookTitle: TextView
    private lateinit var bookAuthor: TextView
    private lateinit var bookSummary: TextView

    private lateinit var readers: TextView
    private lateinit var likes: TextView
    private lateinit var quotes: TextView
    private lateinit var genre: TextView

    private var bookId = 0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_detail, container, false)
        headerRecyclerView = view.findViewById(R.id.header_recycler_view)
        recommendedRecyclerView = view.findViewById(R.id.recommended_recycler_view)
        bookTitle = view.findViewById(R.id.book_title)
        bookAuthor = view.findViewById(R.id.book_author)
        bookSummary = view.findViewById(R.id.book_summary)
        readers = view.findViewById(R.id.readers_title)
        likes = view.findViewById(R.id.likes_title)
        quotes = view.findViewById(R.id.quotes_title)
        genre = view.findViewById(R.id.genre_title)

        view.findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            findNavController().popBackStack()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookId = arguments!!["bookId"]  as Int
        updateViewSize(bookId)
        viewModel.headerCarouselBooks.value?.get(bookId)?.let {
            updateBookDetails(it)
        }
        setupRecyclerViews()
        observeViewModel()
    }

    private fun setupRecyclerViews() {
        headerRecyclerView.setHasFixedSize(false)
        val snapHelper: SnapHelper = PagerSnapHelper()
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        headerRecyclerView.layoutManager = layoutManager
        snapHelper.attachToRecyclerView(headerRecyclerView)

        headerRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val centerView = snapHelper.findSnapView(layoutManager)
                    val pos: Int = centerView?.let { layoutManager.getPosition(it) } ?: 0
                    viewModel.headerCarouselBooks.value?.get(pos)
                            ?.let { updateBookDetails(it) }
                    updateViewSize(pos)
                }
            }
        })

        recommendedRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recommendedRecyclerView.setHasFixedSize(true)
    }

    private fun observeViewModel() {
        viewModel.headerCarouselBooks.observe(viewLifecycleOwner) { books ->
          //  updateViewSize(bookId)
            viewModel.headerCarouselBooks.value?.get(bookId)?.let {
                updateBookDetails(it)
                headerRecyclerView.scrollToPosition(bookId)
                updateViewSize(bookId)
            }
            val adapter = HeaderCarouselAdapter(books) { selectedBook ->
                updateBookDetails(selectedBook)
            }
            headerRecyclerView.adapter = adapter
        }

        viewModel.headerCarouselBooks.observe(viewLifecycleOwner) { books ->
            val adapter = BookAdapter(books, true) { book ->
                updateBookDetails(book)
                headerRecyclerView.scrollToPosition(book.id)
                updateViewSize(book.id)
            }
            recommendedRecyclerView.adapter = adapter
        }
    }

    private fun updateViewSize(position: Int) {
        val layoutManager = headerRecyclerView.layoutManager ?: return

        for (i in 0 until layoutManager.childCount) {
            val view = layoutManager.getChildAt(i)
            val adapterPosition = headerRecyclerView.getChildAdapterPosition(view!!)

            if (adapterPosition == position) {
                view.scaleX = 1.1f
                view.scaleY = 1.1f
            } else {
                view.scaleX = 0.9f
                view.scaleY = 0.9f
            }
        }
    }

    private fun updateBookDetails(book: Book) {
        bookTitle.text = book.name
        bookAuthor.text = book.author
        bookSummary.text = book.summary
        readers.text = book.views
        likes.text = book.likes
        quotes.text = book.quotes
        genre.text = book.genre
    }
}

