package uz.ismoil.notes.viewmodels

import androidx.lifecycle.LiveData
import uz.ismoil.notes.data.entities.NoteEntity

interface TrashScreenViewModel {

    fun getTrashNotes(): LiveData<List<NoteEntity>>
}