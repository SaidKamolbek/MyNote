package com.example.mynote.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynote.model.Note
import com.example.mynote.model.RelationNoteAndCheckList
import com.example.mynote.model.Text

@Dao
interface TextDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheckList(item: Text): Long

    @Query("SELECT * FROM note where saveType = 0")
    fun getNotes(): LiveData<List<Note>>


    @Query("select * from note where type = 1")
    fun getFavNotes(): LiveData<List<Note>>

    @Query("select * from note where saveType = 1")
    fun getArchiveNotes(): LiveData<List<Note>>

    @Query("select * from note where saveType = 2")
    fun getDeletedNotes(): LiveData<List<Note>>

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("delete from note where saveType = 2")
    suspend fun deleteAllNotes()

}