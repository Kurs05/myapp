package com.example.myapp.viewmodel
// viewmodel/BookViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.myapp.data.BookRepository

class BookViewModel(private val repository: BookRepository) : ViewModel() {

    private val _bookContent = MutableStateFlow<String>("")
    val bookContent = _bookContent.asStateFlow()

    fun loadBook() {
        viewModelScope.launch {
            val book = repository.getFirstBook()
            _bookContent.value = book?.content ?: "Книга не найдена"
        }
    }
}
