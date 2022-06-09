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
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: NoteRepository
) : ViewModel() {

    // todo: maybe use LinkedList
    private var notesListLiveData = MutableLiveData<ArrayList<NoteModel>>()

    fun getNotesListLiveData(): LiveData<ArrayList<NoteModel>> {
        return notesListLiveData
    }

    fun addNote() {

    }

    fun editNote(i: Int, note: NoteModel) {
        notesListLiveData.value?.let {
            it.add(i, note)
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
        viewModelScope.launch {
            notesListLiveData.value?.let {
                repo.setNotes(NotesModel(it))
            }
        }
    }

    fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getNotes().list as ArrayList<NoteModel>

            notesListLiveData.postValue(result)
        }
    }

//    suspend fun getLiveDataValue(): ArrayList<NoteModel>? {
//        return suspendCoroutine {
//            notesListLiveData.value?.let { list ->
//                it.resume(list)
//            }
//        }
//    }

}