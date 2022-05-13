package com.example.mynote.repository

import androidx.lifecycle.LiveData
import com.example.mynote.database.TextDao
import com.example.mynote.model.Note
import com.example.mynote.model.RelationNoteAndCheckList
import com.example.mynote.model.Text

class NoteRepository(private val dao: TextDao) {

    val readALlNote: LiveData<List<Note>> = dao.getNotes()

    suspend fun addNote(note: Note) {
        dao.insertNote(note)
    }


    suspend fun updateNote(note: Note) {
        dao.updateNote(note)
    }


    fun getFavNotes(): LiveData<List<Note>> {
        return dao.getFavNotes()
    }

    fun getArchiveNotes(): LiveData<List<Note>> {
        return dao.getArchiveNotes()
    }

    fun getDeletedNotes(): LiveData<List<Note>> = dao.getDeletedNotes()


    suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    suspend fun deleteEverything() {
        dao.deleteAllNotes()
    }


}