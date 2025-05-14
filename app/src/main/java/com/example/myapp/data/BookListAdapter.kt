package com.example.myapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.data.BookEntity
import com.example.myapp.databinding.ItemBookBinding

class BookListAdapter(
    private val onBookClick: (BookEntity) -> Unit
) : RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    private var books = emptyList<BookEntity>()

    fun submitList(bookList: List<BookEntity>) {
        books = bookList
        notifyDataSetChanged()
    }

    class BookViewHolder(
        private val binding: ItemBookBinding,
        private val onClick: (BookEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookEntity) {
            binding.bookTitle.text = book.title
            binding.root.setOnClickListener { onClick(book) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding, onBookClick)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int = books.size
}

