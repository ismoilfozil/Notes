package uz.ismoil.notes.viewmodels

import androidx.lifecycle.LiveData
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.models.Note

interface MainScreenViewModel {

    val openCreateScreenLiveData: LiveData<Unit>

    val openReadeScreenLiveData: LiveData<NoteEntity>

    fun openCreateScreen()

    fun openReadeScreen(note:NoteEntity)

    fun getAllNotes(): LiveData<List<NoteEntity>>

    fun getActiveNotes():LiveData<List<NoteEntity>>

    fun deleteNote(note: NoteEntity)

    fun archiveNote(note: NoteEntity)
}