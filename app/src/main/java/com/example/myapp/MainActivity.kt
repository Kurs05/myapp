package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.data.AppDatabase
import com.example.myapp.data.BookEntity
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.data.adapter.BookAdapter
import kotlinx.coroutines.launch
import androidx.core.widget.addTextChangedListener


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BookAdapter
    private var bookList: List<BookEntity> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Настраиваем RecyclerView
        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)


        adapter = BookAdapter(bookList) { book ->
           //тут короче в поток добавил
            lifecycleScope.launch {
                val db = AppDatabase.getDb(this@MainActivity)
                    // и перевод
                val translation = db.bookDao().getTranslationByLanguage(book.id)


                // При клике открываем BookDetailActivity и передаём id и title
            val intent = Intent(this@MainActivity, BookDetailActivity::class.java).apply {
                putExtra("bookId", book.id)
                putExtra("bookTitle", book.title)
                    //и контент
                putExtra("bookContent",translation?.content)
            }
            startActivity(intent)}
        }
        binding.bookRecyclerView.adapter = adapter

        // Получаем книги из БД
        lifecycleScope.launch {
            try {
                val db = AppDatabase.getDb(this@MainActivity)
                val books = db.bookDao().getAllBooks()
                bookList = books
                adapter.updateBooks(bookList)
            } catch (e: Exception) {
                Log.e("MainActivity", "Ошибка при загрузке книг: ${e.message}")
            }
        }

        binding.searchEditText.addTextChangedListener { editable ->
            val query = editable.toString().trim()
            lifecycleScope.launch {
                val db = AppDatabase.getDb(this@MainActivity)
                val filteredBooks = if (query.isNotEmpty()) {
                    db.bookDao().searchBooksByTitle(query)
                } else {
                    db.bookDao().getAllBooks()
                }
                adapter.updateBooks(filteredBooks)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("MyLogAct", "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MyLogAct", "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyLogAct", "onDestroy")
    }
}
