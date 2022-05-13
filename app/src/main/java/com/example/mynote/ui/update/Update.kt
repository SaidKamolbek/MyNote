package com.example.mynote.ui.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mynote.R
import com.example.mynote.databinding.FragmentUpdateBinding
import com.example.mynote.model.Note
import com.example.mynote.viewModel.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import yuku.ambilwarna.AmbilWarnaDialog


class Update : Fragment() {

    private val args: UpdateArgs by navArgs()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NoteViewModel
    private var defaultColor: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        defaultColor = args.note.color
        binding.insertChangeC.setBackgroundColor(defaultColor)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.insertText.setText(args.note.text)
        binding.insertTitle.setText(args.note.title)

        binding.addCheckItem.visibility = View.GONE
        ifNoteScreenOpens()
        binding.insertChangeC.setOnClickListener {
            showColorDialog()
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("yes") { _, _ ->
            viewModel.updateNote(args.note.copy(saveType = 2))
            Snackbar.make(binding.root, "Successfully moved to trash", Snackbar.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
        builder.setNegativeButton("no") { _, _ ->

        }
        builder.setTitle("Delete note")
        builder.setMessage("Are you sure you want to delete ${args.note.title}")
        builder.create().show()

    }

    private fun showColorDialog() {
        val dialog = AmbilWarnaDialog(
            requireContext(),
            defaultColor,
            object : AmbilWarnaDialog.OnAmbilWarnaListener {
                override fun onCancel(dialog: AmbilWarnaDialog?) {

                }

                override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                    defaultColor = color
                    binding.insertChangeC.setBackgroundColor(color)
                }

            })
        dialog.show()
    }

    private fun ifNoteScreenOpens() {
        // < to like the note
        var state = args.note.type
        var isArchive = args.note.saveType

        if (state == 1) {
            binding.updateLike.setImageResource(R.drawable.ic_baseline_star_24)
        } else {
            binding.updateLike.setImageResource(R.drawable.ic_baseline_star_outline_24)
        }
        if (isArchive == 0) {
            binding.updateArchieve.setImageResource(R.drawable.ic_baseline_archive_24)
        } else {
            binding.updateArchieve.setImageResource(R.drawable.ic_baseline_unarchive_24)
        }
        binding.updateLike.setOnClickListener {
            state = if (state == 0) {
                binding.updateLike.setImageResource(R.drawable.ic_baseline_star_24)
                1
            } else {
                binding.updateLike.setImageResource(R.drawable.ic_baseline_star_outline_24)
                0
            }
        }
        binding.updateArchieve.setOnClickListener {
            isArchive = if (isArchive == 0) {
                binding.updateArchieve.setImageResource(R.drawable.ic_baseline_unarchive_24)
                Snackbar.make(binding.root, "Note Archived", Snackbar.LENGTH_SHORT).show()
                1
            } else {
                binding.updateArchieve.setImageResource(R.drawable.ic_baseline_archive_24)
                Snackbar.make(binding.root, "Note Unarchived", Snackbar.LENGTH_SHORT).show()
                0
            }
        }
        binding.insertTitle.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()) {
                binding.textInputLayout.error = null
            }
        }
        binding.insertAddClose.setOnClickListener {
            val title = binding.insertTitle.text.toString()
            val text = binding.insertText.text.toString()
            if (title.isEmpty()) {
                binding.textInputLayout.error = "Title cannot be empty"
            } else {
                viewModel.updateNote(
                    Note(
                        args.note.id,
                        title = title,
                        text = text,
                        color = defaultColor,
                        type = state,
                        saveType = isArchive,
                        date = args.note.date,
                        time = args.note.time
                    )
                )
                findNavController().navigateUp()
            }
        }
    }
}