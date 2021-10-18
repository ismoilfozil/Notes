package uz.ismoil.notes.viewmodels.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState
import uz.ismoil.notes.domain.EditNoteUseCase
import uz.ismoil.notes.viewmodels.EditNoteScreenViewModel
import javax.inject.Inject

@HiltViewModel
class EditNoteScreenViewModelImpl @Inject constructor(
    private val editNoteUseCase: EditNoteUseCase
) : EditNoteScreenViewModel, ViewModel(){
    override val backLiveData = MutableLiveData<Unit>()

    override fun updateNote(id:Long, title:String, text:String, color:Int, state: NoteState) {
        val image = byteArrayOf()
        val note = NoteEntity(id, title, text, image, color, System.currentTimeMillis(), false, state)
        editNoteUseCase.updateNote(note)
    }

}