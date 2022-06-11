package com.task.notes.data.repository.api

import android.util.Log
import com.task.notes.data.remote.RetrofitApi
import com.task.notes.model.NoteModel
import kotlinx.coroutines.delay
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ApiRepository @Inject constructor(
    private val api: RetrofitApi
) {

    suspend fun request(): ArrayList<NoteModel>? {
        val test = api.request().body()?.list
        delay(2000L)

        return suspendCoroutine {
            try {
                it.resume(test as ArrayList<NoteModel>)
            } catch (e: Exception) {
                Log.e("RequestError:", e.toString())
                it.resume(null)
            }
        }
    }

}