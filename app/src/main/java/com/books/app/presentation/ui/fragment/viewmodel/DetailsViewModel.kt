import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.books.app.FirebaseConfig.remoteConfig
import com.books.app.model.Book
import org.json.JSONObject

class DetailsViewModel() : ViewModel() {

    private val _headerCarouselBooks = MutableLiveData<List<Book>>()
    val headerCarouselBooks: LiveData<List<Book>> get() = _headerCarouselBooks
    var bookId = ""
    private val _recommendedBooks = MutableLiveData<List<Book>>()

    init {
        fetchRemoteConfigData()
        fetchRecommendedBooks()
    }

    private fun fetchRemoteConfigData() {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val jsonData = remoteConfig.getString("details_carousel")
                val jsonObject = JSONObject(jsonData)
                val booksArray = jsonObject.getJSONArray("books")
                val books = mutableListOf<Book>()
                for (i in 0 until booksArray.length()) {
                    val bookObject = booksArray.getJSONObject(i)
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
                _headerCarouselBooks.value = books


            }
        }


    }

    fun fetchRecommendedBooks(): List<Book> {
        // remoteConfig.fetchAndActivate().await()
        val json = remoteConfig.getString("you_will_like_section")
        return parseBooks(json)
    }

    private fun parseBooks(json: String): List<Book> {
        return listOf() // Replace with actual parsing logic
    }
}