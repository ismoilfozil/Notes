package uz.ismoil.notes.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.ismoil.notes.data.entities.NoteEntity

data class Note (
    val id: Long,
    val title: String,
    val content: String,
    val timestamp: Long,
    val pinned: Boolean,
    val color: String
)