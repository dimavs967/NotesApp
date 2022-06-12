package com.task.notes.ui.screens.note

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.task.notes.databinding.FragmentNoteBinding
import com.task.notes.R
import com.task.notes.model.NoteModel
import com.task.notes.ui.activity.MainActivity
import com.task.notes.ui.screens.base.BaseFragment
import com.task.notes.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : BaseFragment<FragmentNoteBinding>() {

    private val viewModel: MainViewModel by activityViewModels()
    private val args: NoteFragmentArgs by navArgs()

    override fun getViewBinding(): FragmentNoteBinding = FragmentNoteBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNotesListLiveData().observe(viewLifecycleOwner) {
            updateUiElements(it[args.position])
        }

        binding.toolbar.setNavigationOnClickListener {
            view.findNavController()
                .navigate(NoteFragmentDirections.actionNoteFragmentToMainFragment())
            view.findNavController().popBackStack(R.id.noteFragment, true)
        }

        binding.editBtn.setOnClickListener {
            (requireActivity() as MainActivity).hideSoftKeyboard()
            clearElementsFocus()
            updateNoteChanges()
        }

        binding.noteTitle.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.editBtn.visibility = View.VISIBLE
        }

        binding.noteDescription.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.editBtn.visibility = View.VISIBLE
        }
    }

    override fun onStop() {
        viewModel.setNotes()
        super.onStop()
    }

    private fun updateNoteChanges() {
        viewModel.editNote(
            args.position,
            binding.noteTitle.text.toString(),
            binding.noteDescription.text.toString()
        )
    }

    private fun clearElementsFocus() {
        binding.editBtn.visibility = View.GONE
        binding.noteTitle.clearFocus()
        binding.noteDescription.clearFocus()
        binding.parentLayout.requestFocus()
    }

    private fun updateUiElements(note: NoteModel) {
        binding.noteTitle.setText(note.title)
        binding.noteDescription.setText(note.description)
        binding.noteDate.text = note.date
    }

}