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
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
//            val hour = c.get(Calendar.HOUR_OF_DAY)
//            val minute = c.get(Calendar.MINUTE)

        val note = NoteModel("New note", "", "$day/$month/$year")
        val currentList = notesListLiveData.value

//        notesListLiveData.value?.also { list ->
        if (currentList != null) {
            currentList.add(0, note)
            notesListLiveData.postValue(currentList!!)
        } else {
            val newList = ArrayList<NoteModel>().also { it.add(note) }
            notesListLiveData.postValue(newList)
        }
//        }
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
                    notesListLiveData.postValue(it.list as ArrayList<NoteModel>)
                }
            }
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