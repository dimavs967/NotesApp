package com.task.notes.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.notes.model.NotesModel

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notesModel: NotesModel)

    @Query("SELECT * FROM notes_table")
    suspend fun getNotes(): NotesModel?

}