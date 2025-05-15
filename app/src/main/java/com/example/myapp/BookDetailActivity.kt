package com.example.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityBookDetailBinding
import android.widget.ArrayAdapter
import android.widget.AdapterView
import androidx.lifecycle.lifecycleScope
import com.example.myapp.data.AppDatabase
import com.example.myapp.data.TranslationEntity
import kotlinx.coroutines.launch

class BookDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookId = intent.getIntExtra("bookId", -1)
        val bookTitle = intent.getStringExtra("bookTitle") ?: "Нет названия"
        binding.bookDetailTitle.text = bookTitle

        // Загружаем переводы
        if (bookId != -1) {
            loadTranslations(bookId)
        } else {
            binding.bookDetailContent.text = "Ошибка: ID книги не найден"
        }
    }
    private fun loadTranslations(bookId: Int) {

        val dao = AppDatabase.getDb(this).bookDao()
        lifecycleScope.launch {
            val translations = dao.getTranslationsForBook(bookId)
            if (translations.isNotEmpty()) {

                val adapter = ArrayAdapter(
                    this@BookDetailActivity,
                    android.R.layout.simple_spinner_item,
                    translations.map { it.language }
                ).apply {
                    setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }

                binding.translationSpinner.adapter = adapter

                // При выборе перевода — обновляем контент
                binding.translationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                        val selectedTranslation = translations[position]
                        binding.bookDetailContent.text = selectedTranslation.content
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // ничего не делаем
                    }
                }

                // Показ первого перевода по умолчанию
                binding.bookDetailContent.text = translations[0].content
            } else {
                binding.bookDetailContent.text = "Переводы не найдены."
            }
        }
    }


}
