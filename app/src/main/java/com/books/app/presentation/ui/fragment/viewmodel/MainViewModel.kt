package com.books.app.presentation.ui.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.books.app.FirebaseConfig.remoteConfig
import com.books.app.model.BannerSlide
import com.books.app.model.Book
import com.books.app.model.Genre
import org.json.JSONObject

class MainViewModel() : ViewModel() {
    private val _bannerSlides = MutableLiveData<List<BannerSlide>>()
    val bannerSlides: LiveData<List<BannerSlide>> get() = _bannerSlides
    val genreSlides: LiveData<List<Genre>> get() = _genreSlides
    private val _genreSlides = MutableLiveData<List<Genre>>()



    init {
        fetchRemoteConfigData()
    }

    private fun fetchRemoteConfigData() {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val jsonData = remoteConfig.getString("json_data")
                val jsonObject = JSONObject(jsonData)
                val booksArray = jsonObject.getJSONArray("books")
                val books = mutableListOf<Book>()
                val set = HashSet<String>()
                for (i in 0 until booksArray.length()) {
                    val bookObject = booksArray.getJSONObject(i)
                    set.add(bookObject.getString("genre"))
                    val book = Book(
                            id = bookObject.getInt("id"),
                            name = bookObject.getString("name"),
                            author = bookObject.getString("author"),
                            summary = bookObject.getString("summary"),
                            genre = bookObject.getString("genre"),
                            coverUrl = bookObject.getString("cover_url"),
                            views = bookObject.getString("views"),
                            likes = bookObject.getString("likes"),
                            quotes = bookObject.getString("quotes")
                    )
                    books.add(book)
                }
                _genreSlides.value = groupBooksByGenre(books)

                val bannerSlidesArray = jsonObject.getJSONArray("top_banner_slides")
                val bannerSlides = mutableListOf<BannerSlide>()
                for (i in 0 until bannerSlidesArray.length()) {
                    val bannerSlideObject = bannerSlidesArray.getJSONObject(i)
                    val bannerSlide = BannerSlide(
                            id = bannerSlideObject.getInt("id"),
                            bookId = bannerSlideObject.getInt("book_id"),
                            coverUrl = bannerSlideObject.getString("cover")
                    )
                    bannerSlides.add(bannerSlide)
                }
                _bannerSlides.value = bannerSlides
            }
        }


    }

    fun groupBooksByGenre(books: List<Book>): List<Genre> {
        return books.groupBy { it.genre }
                .map { Genre(it.key, it.value) }
    }
}
