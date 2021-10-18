package uz.ismoil.notes.domain

import androidx.lifecycle.LiveData
import uz.ismoil.notes.data.entities.NoteEntity

interface EditNoteUseCase {

    fun updateNote(noteEntity: NoteEntity): LiveData<Long>
}