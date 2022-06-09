package com.task.notes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.notes.data.repository.api.ApiRepository
import com.task.notes.data.repository.note.NoteRepository
import com.task.notes.model.NoteModel
import com.task.notes.model.NotesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: NoteRepository,
    private val api: ApiRepository
) : ViewModel() {

    private var notesListLiveData = MutableLiveData<ArrayList<NoteModel>>()

    fun getNotesListLiveData(): LiveData<ArrayList<NoteModel>> {
        return notesListLiveData
    }

    fun request() {
        viewModelScope.launch {
            val result = api.request()

            result?.let {
                notesListLiveData.value?.let { list ->
                    list.addAll(it.list)
                    notesListLiveData.postValue(list)
                }
            } ?: run {

            }
        }
    }

    // todo: rewrite method and add logic for note date
    fun addNote() {

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
//            val hour = c.get(Calendar.HOUR_OF_DAY)
//            val minute = c.get(Calendar.MINUTE)

        val note = NoteModel("New note", "", "$day/$month/$year")

        notesListLiveData.value?.let {
            it.add(0, note)
            notesListLiveData.postValue(it)
        } ?: run {
            val newList = ArrayList<NoteModel>().also { it.add(note) }
            notesListLiveData.postValue(newList)
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
                    notesListLiveData.postValue(it.list as ArrayList<NoteModel>)
                } ?: run {
                    notesListLiveData.postValue(ArrayList())
                }
            }
        }
    }

}