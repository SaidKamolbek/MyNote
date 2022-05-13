package com.example.mynote.ui.starredNotes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mynote.adapter.AllFavNotesAdapter
import com.example.mynote.adapter.AllNotesAdapter
import com.example.mynote.databinding.StarredNotesFragmentBinding
import com.example.mynote.viewModel.NoteViewModel

class StarredNotes : Fragment() {


    private var _binding: StarredNotesFragmentBinding? = null

    private val binding get() = _binding!!
    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = StarredNotesFragmentBinding.inflate(inflater, container, false)
        val root = binding.root
        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getStarredNotes()
    }

    private fun getStarredNotes() {
        val adapter = AllFavNotesAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.readAllFavNote.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) binding.textView1.visibility = View.GONE
            else binding.textView1.visibility = View.VISIBLE
            adapter.submitList(it)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}