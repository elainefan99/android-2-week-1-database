package com.ucsdextandroid2.todoroom

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Created by rjaylward on 2019-07-05
 */
abstract class AppDatabase {

    companion object {

        private const val DB_NAME = "notesapp.db"

        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }
        val migration From 1to2(): Migration(){
            .allowMainThreadQueries()
                    .addMigrations(migrationFrom1to2)
                    .build()
            override fun migrate(database: SupportSQLiteDatabase){
                database.execSQL("Alter Table book add column pub_year Integer")
            }
        }
        private fun buildDatabase(context: Context): AppDatabase = TODO()
    }

}
