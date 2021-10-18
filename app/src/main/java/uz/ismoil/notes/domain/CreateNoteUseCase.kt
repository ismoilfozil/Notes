package uz.ismoil.notes.domain

import androidx.lifecycle.LiveData
import uz.ismoil.notes.data.entities.NoteEntity

interface CreateNoteUseCase {

    fun addNote(noteEntity: NoteEntity): LiveData<Long>
}