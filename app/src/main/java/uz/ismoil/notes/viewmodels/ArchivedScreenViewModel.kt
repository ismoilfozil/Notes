package uz.ismoil.notes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import uz.ismoil.notes.data.entities.NoteEntity

interface ArchivedScreenViewModel {

    val backLiveData: LiveData<Unit>

    fun getArchiveNotes(): LiveData<List<NoteEntity>>
    fun delete(data: NoteEntity)
    fun unarchive(data: NoteEntity)
    fun searchDatabase(searchQuery: String): LiveData<List<NoteEntity>>
}