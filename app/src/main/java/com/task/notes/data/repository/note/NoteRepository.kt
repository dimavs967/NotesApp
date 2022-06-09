package com.task.notes.data.repository.note

import com.task.notes.data.local.db.NoteDao
import com.task.notes.model.NotesModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    suspend fun setNotes(notesModel: NotesModel) {
        noteDao.insertNotes(notesModel)
    }

    // todo: rewrite method
    suspend fun getNotes(): NotesModel? {
        val result = noteDao.getNotes()

        delay(5000L)

        return suspendCoroutine {
            result.let { list ->
                it.resume(list)
            }
        }
    }

}