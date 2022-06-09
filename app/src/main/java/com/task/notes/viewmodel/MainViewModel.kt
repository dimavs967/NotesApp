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
    private var notesList = MutableLiveData<ArrayList<NoteModel>>()

    fun getNotesListLiveData(): LiveData<ArrayList<NoteModel>> {
        return notesList
    }

    suspend fun editNote(index: Int, note: NoteModel) = withContext(Dispatchers.IO) {
        notesList.also {
            val list = it.value

            if (list != null) {
                list[index] = note
                it.postValue(list)
            }
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

        notesList.value.also { data ->
            if (data != null) {
                data.add(0, note)
                notesList.postValue(data)
            } else {
                val list = ArrayList<NoteModel>().also { it.add(note) }
                notesList.postValue(list)
            }
        }
    }

    fun deleteNote(index: Int) {
        notesList.value?.removeAt(index)
    }


    fun editList() {

    }

    fun insertNotes() {
        viewModelScope.launch {
            val currentNotes = notesList.value
            repo.insertNotes(NotesModel(list = currentNotes!!))
        }
    }

    fun getNotes() {
        viewModelScope.launch {
            notesList.value.also {
                if (it == null) {
                    val result = repo.getNotes()
                    notesList.postValue(result)
                }
            }
        }
    }

}