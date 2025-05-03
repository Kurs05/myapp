package com.example.myapp.data


class BookRepository(private val dao: BookDao) {
    suspend fun getFirstBook(): TranslationEntity? {
        return dao.getFirstBook()
    }

    suspend fun insertBook(book: BookEntity) {
        dao.insertBook(book)
    }
}
