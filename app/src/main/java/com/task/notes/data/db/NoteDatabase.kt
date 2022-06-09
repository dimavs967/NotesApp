package com.task.notes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.notes.model.NotesModel
import com.task.notes.utils.ListConverter

@Database(entities = [NotesModel::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}