package com.example.myapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {

    // Получить первую доступную запись перевода
    @Query("SELECT * FROM Translations LIMIT 1")
    suspend fun getFirstBook(): TranslationEntity?

    // Вставка книги
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    // Вставка перевода
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslation(translation: TranslationEntity)

    // Получить все книги
    @Query("SELECT * FROM Books")
    suspend fun getAllBooks(): List<BookEntity>

    // Получить книгу по ID
    @Query("SELECT * FROM Books WHERE id = :id")
    suspend fun getBookById(id: Int): BookEntity?

    // Получить все переводы конкретной книги
    @Query("SELECT * FROM Translations WHERE book_id = :bookId")
    suspend fun getTranslationsForBook(bookId: Int): List<TranslationEntity>

    // Получить перевод по языку
    @Query("SELECT * FROM Translations WHERE book_id = :bookId AND language = :lang LIMIT 1")
    suspend fun getTranslationByLanguage(bookId: Int, lang: String = "Ru"): TranslationEntity?

    // 🔍 Поиск книг по названию
    @Query("SELECT * FROM Books WHERE title LIKE '%' || :query || '%'")
    suspend fun searchBooksByTitle(query: String): List<BookEntity>
}
