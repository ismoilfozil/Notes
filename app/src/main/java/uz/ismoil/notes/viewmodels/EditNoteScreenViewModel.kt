package uz.ismoil.notes.viewmodels

import androidx.lifecycle.LiveData
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState

interface EditNoteScreenViewModel {
    val backLiveData: LiveData<Unit>

    fun updateNote(id:Long, title:String, text:String,time:String, color:Int, state:NoteState)
}