package com.example.myapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.data.AppDatabase
import com.example.myapp.data.BookEntity
import com.example.myapp.databinding.ActivityBookCatalogBinding
import kotlinx.coroutines.launch

class BookCatalogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookCatalogBinding
    private lateinit var adapter: BookListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = BookListAdapter { book ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("book_id", book.id)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            val books = AppDatabase.getDb(this@BookCatalogActivity).bookDao().getAllBooks()
            adapter.submitList(books)
        }
    }
}
