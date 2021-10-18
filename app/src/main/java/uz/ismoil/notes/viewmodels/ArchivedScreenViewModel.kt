package uz.ismoil.notes.viewmodels

import androidx.lifecycle.LiveData
import uz.ismoil.notes.data.entities.NoteEntity

interface ArchivedScreenViewModel {

    fun getArchiveNotes(): LiveData<List<NoteEntity>>
}