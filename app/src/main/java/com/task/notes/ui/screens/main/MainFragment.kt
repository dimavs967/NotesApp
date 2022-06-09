package com.task.notes.ui.screens.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.task.notes.R
import com.task.notes.databinding.FragmentMainBinding
import com.task.notes.ui.screens.adapter.NotesAdapter
import com.task.notes.utils.NetworkUtility
import com.task.notes.utils.SwipeToDelete
import com.task.notes.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private var adapter: NotesAdapter? = null
    private var itemHelper: SwipeToDelete? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        adapter = NotesAdapter()

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter

        itemHelper = SwipeToDelete(
            adapter = adapter!!,
            deleteIconRes = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_delete_sweep
            )!!
        )

        ItemTouchHelper(itemHelper!!).attachToRecyclerView(binding.recyclerView)

        viewModel.getNotesListLiveData().observe(viewLifecycleOwner) {
            adapter!!.initAdapter(it)

            if (!it.isNullOrEmpty()) {
                binding.addNoteBtn.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.infoText.visibility = View.GONE
            } else {
                binding.addNoteBtn.visibility = View.VISIBLE
                binding.infoText.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.infoText.text = resources.getString(R.string.add_new_note_text)
            }
        }

        NetworkUtility().getNetworkStateLiveData(requireContext()).observe(viewLifecycleOwner) {
            if (it) {
                if (viewModel.getNotesListLiveData().value.isNullOrEmpty()) { // no data
                    viewModel.getNotes()

                    binding.progressBar.visibility = View.VISIBLE
                    binding.addNoteBtn.visibility = View.GONE
                    binding.infoText.visibility = View.GONE
                }
            } else {
                if (viewModel.getNotesListLiveData().value.isNullOrEmpty()) {
                    binding.addNoteBtn.visibility = View.GONE
                    binding.infoText.text = resources.getString(R.string.connection_text)
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.addNoteBtn.tooltipText = "Add new note"
        }

        binding.addNoteBtn.setOnClickListener {
//            viewModel.addNote()
            viewModel.request()
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
        _binding = null
        adapter = null
    }

}