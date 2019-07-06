package com.ucsdextandroid2.todoroom

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by rjaylward on 2019-07-05
 */
@Dao
interface NotesDao {
@Insert
@Insert(onConflict)
@query ("SELECT * FROM notes ORDER BY datetime DESC")
fun getAllNotesLiveData()LIveData<List<Note>>
}
