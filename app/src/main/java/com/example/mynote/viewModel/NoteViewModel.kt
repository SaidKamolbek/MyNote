package com.example.mynote.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mynote.database.MyDatabase
import com.example.mynote.model.Note
import com.example.mynote.model.RelationNoteAndCheckList
import com.example.mynote.model.Text
import com.example.mynote.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    val readAllNote: LiveData<List<Note>>
    val readAllFavNote: LiveData<List<Note>>
    val readAllArchiveNote: LiveData<List<Note>>
    val readDeletedNotes: LiveData<List<Note>>


    init {
        val dao = MyDatabase.getDatabase(application).dao()
        repository = NoteRepository(dao)
        readAllNote = repository.readALlNote
        readAllFavNote = repository.getFavNotes()
        readAllArchiveNote = repository.getArchiveNotes()
        readDeletedNotes = repository.getDeletedNotes()
    }


    fun addNote(note: Note) {
        viewModelScope.launch {
            repository.addNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }


    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun deleteEverything() {
        viewModelScope.launch {
            repository.deleteEverything()
        }
    }


}