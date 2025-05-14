package com.example.myapp.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {
    @Query("SELECT * FROM Translations LIMIT 1")
    suspend fun getFirstBook(): TranslationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    @Query("SELECT * FROM Books")
    suspend fun getAllBooks(): List<BookEntity>

    @Query("SELECT * FROM Books WHERE id = :id")
    suspend fun getBookById(id: Int): BookEntity?

    @Query("SELECT * FROM Translations WHERE book_id = :bookId")
    suspend fun getTranslationsForBook(bookId: Int): List<TranslationEntity>

    @Query("SELECT * FROM Translations WHERE book_id = :bookId AND language = :lang LIMIT 1")
    suspend fun getTranslationByLanguage(bookId: Int, lang: String = "Ru"): TranslationEntity?
}
