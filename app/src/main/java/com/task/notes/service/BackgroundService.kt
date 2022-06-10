package com.task.notes.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.task.notes.data.repository.note.NoteRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BackgroundService @Inject constructor(
    private val repo: NoteRepository
) : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.e("test", "service start")
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return START_NOT_STICKY // todo: check it in docs
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("test", "service destroy")
    }

}