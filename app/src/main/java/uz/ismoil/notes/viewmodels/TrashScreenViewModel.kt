package uz.ismoil.notes.viewmodels

import androidx.lifecycle.LiveData
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState

interface TrashScreenViewModel {

    val backLiveData: LiveData<Unit>

    fun getTrashNotes(): LiveData<List<NoteEntity>>

    fun deleteAll()

    fun restore(data:NoteEntity)
    fun delete(data: NoteEntity)
    fun searchDatabase(searchQuery: String): LiveData<List<NoteEntity>>

}