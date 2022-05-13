package com.example.mynote.model

import androidx.room.Embedded
import androidx.room.Relation

data class RelationNoteAndCheckList(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "id",
        entityColumn = "noteId"
    ) val checkList: List<Text>
)