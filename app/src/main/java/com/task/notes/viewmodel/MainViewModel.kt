package com.task.notes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.notes.data.repository.NoteRepository
import com.task.notes.model.NoteModel
import com.task.notes.model.NotesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: NoteRepository
) : ViewModel() {

    // todo: maybe use LinkedList
    private var notesListLiveData = MutableLiveData<ArrayList<NoteModel>?>()

    fun getNotesListLiveData(): LiveData<ArrayList<NoteModel>?> {
        return notesListLiveData
    }

    fun editNote(index: Int, note: NoteModel) {
        val notesList = notesListLiveData.value

        if (notesList != null) {
            notesList[index] = note
            notesListLiveData.postValue(notesList)
        }
    }

    // todo: remake method
    suspend fun addNote() = withContext(Dispatchers.IO) {

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
//            val hour = c.get(Calendar.HOUR_OF_DAY)
//            val minute = c.get(Calendar.MINUTE)

        val note = NoteModel("New note", "", "$day/$month/$year")
        val notesList = notesListLiveData.value

        if (notesList != null) {
            notesList.add(0, note)
            notesListLiveData.postValue(notesList)
        } else {
            val newList = ArrayList<NoteModel>().also { it.add(note) }
            notesListLiveData.postValue(newList)
        }
    }

    fun deleteNote(index: Int) {
        val notesList = notesListLiveData.value
        notesList?.removeAt(index)

        notesListLiveData.postValue(notesList)
    }

    fun insertNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val notesList = notesListLiveData.value

            if (notesList != null) {
                repo.insertNotes(NotesModel(notesList))
            }
        }
    }

    fun getNotes() {
        viewModelScope.launch {
            val result = repo.getNotes()
            if (notesListLiveData.value == null) {
                notesListLiveData.postValue(result)
            }
        }
    }

}