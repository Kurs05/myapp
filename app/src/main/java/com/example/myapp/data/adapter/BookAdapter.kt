package com.example.myapp.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.data.BookEntity

class BookAdapter(
    private var books: List<BookEntity>,
    private val onBookClick: (BookEntity) -> Unit // Передача данных через клик
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.bookTitle)
        val image: ImageView = view.findViewById(R.id.bookImage)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onBookClick(books[position]) // Обработка клика
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.title.text = book.title

        // Загрузка обложки книги
        val context = holder.itemView.context
        if (!book.coverPath.isNullOrEmpty()) {
            try {
                val inputStream = context.assets.open(book.coverPath)
                val drawable = android.graphics.drawable.Drawable.createFromStream(inputStream, null)
                holder.image.setImageDrawable(drawable)
            } catch (e: Exception) {
                holder.image.setImageResource(R.drawable.default_book) // если файл не найден
            }
        } else {
            holder.image.setImageResource(R.drawable.default_book) // если путь пуст
        }
    }

    override fun getItemCount(): Int = books.size

    // Функция для обновления списка книг
    fun updateBooks(newBooks: List<BookEntity>) {
        books = newBooks
        notifyDataSetChanged()
    }
}
