package com.task.notes.data.repository

import com.task.notes.data.db.NoteDao
import com.task.notes.model.NoteModel
import com.task.notes.model.NotesModel
import kotlinx.coroutines.delay
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    suspend fun insertNotes(notesModel: NotesModel) {
        noteDao.insertNotes(notesModel)
    }

    // todo: do null safety
    suspend fun getNotes(): ArrayList<NoteModel> {
        val result = noteDao.getNotes().list
        val notesList = ArrayList<NoteModel>()

        for (element in result) notesList.add(element)

        // Simulation of server request work
        delay(5000L)
        return notesList
    }

}