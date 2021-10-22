package uz.ismoil.notes.viewmodels

import androidx.lifecycle.LiveData
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState

interface CreateNoteScreenViewModel {
    val backLiveData: LiveData<Unit>

    fun addNote(title:String, text:String, time:String,color:Int, state: NoteState, pinned:Boolean)
}