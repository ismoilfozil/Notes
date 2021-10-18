package uz.ismoil.notes.viewmodels

import androidx.lifecycle.LiveData
import uz.ismoil.notes.data.entities.NoteEntity

interface ReadNoteScreenViewModel {
    val openEditScreenLiveData:LiveData<NoteEntity>

    fun openEditScreen(noteEntity: NoteEntity)
}
