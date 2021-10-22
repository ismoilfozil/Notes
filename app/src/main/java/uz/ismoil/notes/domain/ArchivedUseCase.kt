package uz.ismoil.notes.domain

import androidx.lifecycle.LiveData
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState

interface ArchivedUseCase {

    fun getNotesByState(state: NoteState): LiveData<List<NoteEntity>>
    fun searchDatabase(searchQuery: String): LiveData<List<NoteEntity>>
    fun unarchive(data: NoteEntity) : LiveData<Unit>
    fun delete(data: NoteEntity) : LiveData<Unit>

}