package com.example.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityBookDetailBinding

class BookDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookTitle = intent.getStringExtra("bookTitle") ?: "Нет названия"
        val bookContent = intent.getStringExtra("bookContent") ?: "Нет текста"

        binding.bookDetailTitle.text = bookTitle
        binding.bookDetailContent.text = bookContent
    }
}
