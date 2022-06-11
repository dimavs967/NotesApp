package com.task.notes.ui.screens.main

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.task.notes.R
import com.task.notes.databinding.FragmentMainBinding
import com.task.notes.ui.screens.adapter.NotesAdapter
import com.task.notes.ui.screens.base.BaseFragment
import com.task.notes.utils.SwipeToDelete
import com.task.notes.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel: MainViewModel by activityViewModels()

    private var adapter: NotesAdapter? = null
    private var itemHelper: SwipeToDelete? = null

    override fun getViewBinding(): FragmentMainBinding =
        FragmentMainBinding.inflate(layoutInflater)

    override fun onStart() {
        super.onStart()
        viewModel.getNotes()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NotesAdapter()

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter

        itemHelper = SwipeToDelete(
            mAdapter = adapter!!,
            deleteIcon = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_delete_sweep
            )!!
        )

        ItemTouchHelper(itemHelper!!).attachToRecyclerView(binding.recyclerView)

        viewModel.getNotesListLiveData().observe(viewLifecycleOwner) {
            adapter?.initAdapter(it)

            if (it.isNullOrEmpty()) {
                binding.infoText.visibility = View.VISIBLE
                binding.infoText.text = resources.getString(R.string.add_new_note_text)
            } else {
                binding.infoText.visibility = View.GONE
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.addNoteBtn.tooltipText = "Add new note"
        }

        binding.addNoteBtn.setOnClickListener {
            viewModel.addNote()
        }

        itemHelper?.onRemoveItemListener {
            viewModel.removeNote(it)
        }

        adapter?.setOnClickListener {
            view.findNavController()
                .navigate(MainFragmentDirections.actionMainFragmentToNoteFragment(it))
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.setNotes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        itemHelper = null
        adapter = null
    }

}