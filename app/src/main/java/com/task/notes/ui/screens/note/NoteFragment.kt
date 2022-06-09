package com.task.notes.ui.screens.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.task.notes.databinding.FragmentNoteBinding
import com.task.notes.R
import com.task.notes.model.NoteModel
import com.task.notes.ui.main.MainActivity
import com.task.notes.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private val args: NoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(layoutInflater)

        viewModel.getNotesListLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                updateUiElements(it[args.position])
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            view.findNavController()
                .navigate(NoteFragmentDirections.actionNoteFragmentToMainFragment())
            view.findNavController().popBackStack(R.id.noteFragment, true)
        }

        binding.editBtn.setOnClickListener {
            binding.editBtn.visibility = View.GONE
            binding.noteTitle.clearFocus()
            binding.noteDescription.clearFocus()
            binding.parentLayout.requestFocus()

            (requireActivity() as MainActivity).hideSoftKeyboard()
            updateNoteChanges()
        }

        binding.noteTitle.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.editBtn.visibility = View.VISIBLE
        }

        binding.noteDescription.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.editBtn.visibility = View.VISIBLE
        }
    }

    private fun updateNoteChanges() {
        lifecycleScope.launch {
            viewModel.editNote(
                args.position,
                NoteModel(
                    binding.noteTitle.text.toString(),
                    binding.noteDescription.text.toString(),
                    binding.noteDate.text.toString()
                )
            )
        }
    }

    private fun updateUiElements(note: NoteModel) {
        binding.noteTitle.setText(note.title)
        binding.noteDescription.setText(note.description)
        binding.noteDate.text = note.date
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}