package com.task.notes.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import com.task.notes.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun hideSoftKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    fun showSnackBar(message: String) {
        Snackbar.make(
            this.findViewById(android.R.id.content),
            message, Snackbar.LENGTH_LONG
        ).show()
    }

    // todo: refactor code

}