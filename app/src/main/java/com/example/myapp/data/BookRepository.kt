package com.example.myapp.data


class BookRepository(private val dao: BookDao) {
    suspend fun getFirstBook(): BookEntity? {
        return dao.getFirstBook()
    }

    suspend fun insertBook(book: BookEntity) {
        dao.insertBook(book)
    }
}
