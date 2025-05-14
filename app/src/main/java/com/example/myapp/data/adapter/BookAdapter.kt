package com.example.myapp.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.data.BookEntity

class BookAdapter(private var books: List<BookEntity>) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.bookTitle)
        val image: ImageView = view.findViewById(R.id.bookImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.title.text = book.title
        //новое ! про обложку
        val context = holder.itemView.context

        if (!book.coverPath.isNullOrEmpty()) {
            try {
                val inputStream = context.assets.open(book.coverPath)
                val drawable = android.graphics.drawable.Drawable.createFromStream(inputStream, null)
                holder.image.setImageDrawable(drawable)
            } catch (e: Exception) {
                holder.image.setImageResource(R.drawable.default_book) // если нет файла
            }
        } else {
            holder.image.setImageResource(R.drawable.default_book) // если путь пуст
        }
        // Пока не отображаем изображение
    }

    override fun getItemCount(): Int = books.size

    fun updateBooks(newBooks: List<BookEntity>) {
        books = newBooks
        notifyDataSetChanged()
    }
}
