package com.example.myapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.myapp.data.AppDatabase
import com.example.myapp.data.BookEntity
import com.example.myapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import android.text.method.ScrollingMovementMethod


class MainActivity : AppCompatActivity() {
    var counter = 0
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.bookTextView.text = "Это текст из кода"
        val db = AppDatabase.getDb(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.bookTextView.movementMethod = ScrollingMovementMethod()
        lifecycleScope.launch {
            try {
                val book = db.bookDao().getFirstBook()
                if (book != null) {
                    binding.bookTextView.text = book.content
                } else {
                    binding.bookTextView.text = "Книга не найдена"
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Ошибка при получении книги: ${e.message}")
                binding.bookTextView.text = "Ошибка при загрузке данных"
            }
        }


    }

    override fun onStart()
    {
        super.onStart()
        Log.d("MyLogAct","onStart")
    }
    override fun onPause()
    {
        super.onPause()
        Log.d("MyLogAct","onPause")
    }
    override fun onDestroy()
    {
        super.onDestroy()
        Log.d("MyLogAct","onDestroy")
    }


}
