package com.example.mynote.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Text(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val noteId: Int,
    val checkText: String,
    val checkState: Boolean
)