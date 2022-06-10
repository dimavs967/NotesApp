package com.task.notes.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.widget.Toast

class MyJobService: JobService() {

    override fun onStartJob(p0: JobParameters?): Boolean {

        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        TODO("Not yet implemented")

        return false
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}