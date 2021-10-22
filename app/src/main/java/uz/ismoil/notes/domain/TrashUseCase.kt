package uz.ismoil.notes.domain

import androidx.lifecycle.LiveData
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState

interface TrashUseCase {

    fun getNotesByState(state: NoteState): LiveData<List<NoteEntity>>

    fun deleteALl():LiveData<Unit>

    fun delete(data:NoteEntity): LiveData<Unit>

    fun restore(data: NoteEntity): LiveData<Unit>

    fun searchDatabase(searchQuery: String):LiveData<List<NoteEntity>>
}