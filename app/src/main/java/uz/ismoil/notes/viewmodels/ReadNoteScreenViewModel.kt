package uz.ismoil.notes.viewmodels

import androidx.lifecycle.LiveData
import uz.ismoil.notes.data.entities.NoteEntity

interface ReadNoteScreenViewModel {
    val openEditScreenLiveData:LiveData<NoteEntity>
    val backLiveData:LiveData<Unit>

    fun openEditScreen(noteEntity: NoteEntity)
    fun delete(data:NoteEntity)
    fun archive(data: NoteEntity)
}
