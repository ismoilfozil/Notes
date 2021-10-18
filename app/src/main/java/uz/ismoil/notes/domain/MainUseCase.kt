package uz.ismoil.notes.domain

import androidx.lifecycle.LiveData
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState
import uz.ismoil.notes.models.Note

interface MainUseCase {

    fun getAllNotes(): LiveData<List<NoteEntity>>

    fun getNotesByState(state:NoteState):LiveData<List<NoteEntity>>

    fun deleteNote(noteEntity: NoteEntity):LiveData<Unit>

    fun archiveNote(noteEntity: NoteEntity):LiveData<Unit>

}