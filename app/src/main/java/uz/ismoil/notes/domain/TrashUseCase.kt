package uz.ismoil.notes.domain

import androidx.lifecycle.LiveData
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState

interface TrashUseCase {
    fun getNotesByState(state: NoteState): LiveData<List<NoteEntity>>
}