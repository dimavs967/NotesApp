package com.task.notes.viewmodel

import androidx.lifecycle.*
import com.task.notes.data.repository.api.ApiRepository
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
    private val repo: NoteRepository,
    private val api: ApiRepository
) : ViewModel() {

    private var notesListLiveData = MutableLiveData<ArrayList<NoteModel>>()

    fun getNotesListLiveData(): LiveData<ArrayList<NoteModel>> {
        return notesListLiveData
    }

    fun addNote() {
        val note = NoteModel(
            "New note",
            "",
            DateHelper().getCurrentDate()
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

            val remoteData = api.request()

            val localData = repo.getNotes()?.let {

            }

//            localData?.let {
//                remoteData?.let { r ->
//                    it.addAll(0, r)
//                    notesListLiveData.postValue(it)
//                } ?: run {
//                    notesListLiveData.postValue(it)
//                }
//            } ?: run {
//                remoteData?.let {
//                    notesListLiveData.postValue(it)
//                }
//            }
//
//            if (localData == null || remoteData == null) {
//                notesListLiveData.postValue(ArrayList())
//            }



//            result?.let {
//                notesListLiveData.value?.let { list ->
////                    list.addAll(it.list)
//
//                    list.addAll(0, it.list) // try it
//                    notesListLiveData.postValue(list)
//                } ?: run {
//                    val list = mutableListOf<NoteModel>()
//
//                }
//            }

//            if (notesListLiveData.value == null) {
//                repo.getNotes()?.let {
//                    notesListLiveData.postValue(it.list as ArrayList<NoteModel>)
//                } ?: run {
//                    notesListLiveData.postValue(ArrayList())
//                }
//            }
        }
    }

}