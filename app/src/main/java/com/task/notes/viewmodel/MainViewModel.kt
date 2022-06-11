package com.task.notes.viewmodel

import androidx.lifecycle.*
import com.task.notes.data.repository.note.NoteRepository
import com.task.notes.model.NoteModel
import com.task.notes.model.NotesModel
import com.task.notes.utils.DateHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: NoteRepository
) : ViewModel() {

    private var notesListLiveData = MutableLiveData<ArrayList<NoteModel>>()

    fun getNotesListLiveData(): LiveData<ArrayList<NoteModel>> {
        return notesListLiveData
    }

    fun addNote() {
        val note = NoteModel(
            "New note",
            "",
            DateHelper.getCurrentDate()
        )

        notesListLiveData.value?.let {
            it.add(0, note)
            notesListLiveData.postValue(it)
        } ?: run {
            notesListLiveData.postValue(ArrayList<NoteModel>().also {
                it.add(note)
            })
        }
    }

    fun editNote(i: Int, note: NoteModel) {
        notesListLiveData.value?.let {
            it[i] = note
            notesListLiveData.postValue(it)
        }
    }

    fun removeNote(i: Int) {
        notesListLiveData.value?.let {
            it.remove(it[i])
            notesListLiveData.postValue(it)
        }
    }

    fun setNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            notesListLiveData.value?.let {
                repo.setNotes(NotesModel(it))
            }
        }
    }

    fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            if (notesListLiveData.value == null) {
                repo.getNotes()?.let {
                    notesListLiveData.postValue(it)
                } ?: run {
                    notesListLiveData.postValue(ArrayList())
                }
            }
        }
    }

}