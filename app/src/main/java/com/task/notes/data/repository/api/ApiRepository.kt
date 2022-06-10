package com.task.notes.data.repository.api

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.notes.data.remote.RetrofitApi
import com.task.notes.model.NoteModel
import com.task.notes.model.NotesModel
import com.task.notes.service.BackgroundService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ApiRepository @Inject constructor(
    private val api: RetrofitApi
) {

    private val testData = MutableLiveData<NotesModel>()

    fun getLiveData(): LiveData<NotesModel> {
        return testData
    }

    // todo: rewrite method
    suspend fun request(): NotesModel? {


        delay(3000L)
        val test = api.request().body()?.list
        delay(3000L)

        return suspendCoroutine {
            try {
                val list = mutableListOf<NoteModel>()

                if (test != null) {
                    for (i in test.indices)
                        list.add(test[i])
                }

                testData.postValue(NotesModel(list))
                it.resume(NotesModel(list))

            } catch (e: Exception) {
                Log.e("RequestError:", e.toString())
                it.resume(null)
            }
        }
    }

}