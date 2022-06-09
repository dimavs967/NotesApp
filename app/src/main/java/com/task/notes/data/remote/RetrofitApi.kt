package com.task.notes.data.remote

import com.task.notes.model.NotesModel
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitApi {

    @GET("dimavs967/server_emulation/main/data.json")
    suspend fun request(): Response<NotesModel>

}