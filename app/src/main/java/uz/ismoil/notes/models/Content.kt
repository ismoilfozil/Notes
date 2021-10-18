package uz.ismoil.notes.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Content(
    val id: Long,
    val noteId: Long,
    val type: Int,
    val value: String
)
