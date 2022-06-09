package com.task.notes.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.task.notes.model.NoteModel

class ListConverter {

    @TypeConverter
    fun listToString(value: List<NoteModel>): String {
        val type = object : TypeToken<List<NoteModel>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun fromStringToList(value: String): List<NoteModel> {
        val type = object : TypeToken<List<NoteModel>>() {}.type
        return Gson().fromJson(value, type)
    }

}