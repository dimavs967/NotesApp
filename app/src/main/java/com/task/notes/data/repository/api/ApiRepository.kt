package com.task.notes.data.repository.api

import android.util.Log
import com.task.notes.data.remote.RetrofitApi
import com.task.notes.model.NoteModel
import com.task.notes.model.NotesModel
import kotlinx.coroutines.delay
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ApiRepository @Inject constructor(
    private val api: RetrofitApi
) {

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

                it.resume(NotesModel(list))

            } catch (e: Exception) {
                Log.e("test", e.toString())
                it.resume(null)
            }
        }


    }

}