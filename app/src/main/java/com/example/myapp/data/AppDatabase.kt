package com.example.myapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room

@Database(entities = [BookEntity::class,TranslationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        fun getDb(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "Bookslib.db"
            )
                .createFromAsset("Bookslib.db")
                .fallbackToDestructiveMigration() // опционально
                .build()
        }
    }


}