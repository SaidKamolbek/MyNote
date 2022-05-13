package com.example.mynote.ui.notes

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynote.R
import com.example.mynote.adapter.AllNotesAdapter
import com.example.mynote.databinding.FragmentHomeBinding
import com.example.mynote.model.Note
import com.example.mynote.viewModel.NoteViewModel
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var viewModel: NoteViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.openInsert.setOnClickListener {
            navToInsert(0)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        val adapter = AllNotesAdapter()
        binding.recyclerview.adapter = adapter
        adapter.setListener {
            openLongClickDialog(it)
        }
        viewModel.readAllNote.observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) binding.textView1.visibility = View.GONE
            else binding.textView1.visibility = View.VISIBLE
            adapter.submitList(list)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            deleteEverything()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteEverything() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete everything")
        builder.setMessage("Do you want to delete everything")
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.readAllNote.observe(viewLifecycleOwner) {
                it.forEach { viewModel.updateNote(it.copy(saveType = 2)) }
            }
            Snackbar.make(binding.root, "All notes removed to trash", Snackbar.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("no") { _, _ ->

        }
        builder.create().show()
    }

    private fun openLongClickDialog(note: Note) {
        val dialog = AlertDialog.Builder(requireContext())

        dialog.setItems(arrayOf("Archive note", "Delete note ")) { _, which ->
            if (which == 0) {
                viewModel.updateNote(note.copy(saveType = 1))
            } else if (which == 1) {
                viewModel.updateNote(note.copy(saveType = 2))
            }
        }
        dialog.create()
        dialog.show()
    }

    private fun navToInsert(a: Int) {
        val action = HomeFragmentDirections.actionNavNotesToInsertScreen2(a)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}