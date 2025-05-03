package com.example.myapp.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String?,
    val description: String?
)

@Entity(
    tableName = "ReadingProgress",
    foreignKeys = [ForeignKey(
        entity = BookEntity::class,
        parentColumns = ["id"],
        childColumns = ["book_id"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = TranslationEntity::class,
        parentColumns = ["id"],
        childColumns = ["translation_id"],
        onDelete = ForeignKey.CASCADE
    )]

    ,
    indices = [Index("book_id"), Index("translation_id")]
)
data class ReadingProgressEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "book_id")
    val bookId: Int,
    @ColumnInfo(name = "translation_id")
    val translationId: Int,
    val position: Int,
    val timestamp: Long
)

@Entity(
    tableName = "Translations",
    foreignKeys = [ForeignKey(
        entity = BookEntity::class,
        parentColumns = ["id"],
        childColumns = ["book_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("book_id")]
)
data class TranslationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "book_id")
    val bookId: Int,
    val language: String,
    val content: String
)

@Entity(tableName = "ReadingReminders")
data class ReadingReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val time: Long,
    val active: Boolean
)
