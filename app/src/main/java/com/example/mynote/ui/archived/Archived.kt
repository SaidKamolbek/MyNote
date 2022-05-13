package com.example.mynote.ui.archived

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mynote.R
import com.example.mynote.adapter.ArchivedNotesAdapter
import com.example.mynote.databinding.ArchivedFragmentBinding
import com.example.mynote.model.Note
import com.example.mynote.viewModel.NoteViewModel

class Archived : Fragment() {

    private var _binding: ArchivedFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArchivedFragmentBinding.inflate(inflater, container, false)
        val root = binding.root

        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()

    }

    private fun setUpAdapter() {
        val adapter = ArchivedNotesAdapter()
        binding.archiveRecycler.adapter = adapter
        adapter.setListener { openUnarchiveDialog(it) }
        viewModel.readAllArchiveNote.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) binding.textView3.visibility = View.GONE
            else binding.textView3.visibility = View.VISIBLE
            adapter.submitList(it)
        }
    }

    private fun openUnarchiveDialog(note: Note) {
        val dialog = AlertDialog.Builder(requireContext())

        dialog.setItems(arrayOf("Unarchive note", "Delete note ")) { _, which ->
            if (which == 0) {
                viewModel.updateNote(note.copy(saveType = 0))
            } else if (which == 1) {
                viewModel.updateNote(note.copy(saveType = 2))
            }
        }
        dialog.create()
        dialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}