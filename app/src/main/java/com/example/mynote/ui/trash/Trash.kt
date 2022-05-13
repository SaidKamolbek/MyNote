package com.example.mynote.ui.trash

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mynote.R
import com.example.mynote.adapter.DeletedNotesAdapter
import com.example.mynote.databinding.TrashFragmentBinding
import com.example.mynote.model.Note
import com.example.mynote.viewModel.NoteViewModel

class Trash : Fragment() {

    private var _binding: TrashFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TrashFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            emptyTrash()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpAdapter() {
        val adapter = DeletedNotesAdapter()
        binding.trashRecycler.adapter = adapter
        adapter.setListener {
            openDeleteDialog(it)
        }
        viewModel.readDeletedNotes.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) binding.textView2.visibility = View.GONE
            else binding.textView2.visibility = View.VISIBLE
            adapter.submitList(it)
        }
    }

    private fun emptyTrash() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setPositiveButton("yes") { _, _ ->
            viewModel.deleteEverything()
        }
        dialog.setNegativeButton("no") { _, _ -> }
        dialog.setTitle("Empty trash")
        dialog.setMessage("this will delete all notes forever")
        dialog.create()
        dialog.show()
    }

    private fun openDeleteDialog(note: Note) {
        val dialog = AlertDialog.Builder(requireContext())

        dialog.setItems(arrayOf("Restore Note", "Delete forever")) { _, which ->
            if (which == 0) {
                viewModel.updateNote(note.copy(saveType = 0))
            } else if (which == 1) {
                viewModel.deleteNote(note)
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