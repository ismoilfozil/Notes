package uz.ismoil.notes.viewmodels.impl

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState
import uz.ismoil.notes.domain.EditNoteUseCase
import uz.ismoil.notes.utils.addSourceDisposable
import uz.ismoil.notes.viewmodels.EditNoteScreenViewModel
import javax.inject.Inject

@HiltViewModel
class EditNoteScreenViewModelImpl @Inject constructor(
    private val editNoteUseCase: EditNoteUseCase
) : EditNoteScreenViewModel, ViewModel(){
    override val backLiveData = MediatorLiveData<Unit>()

    override fun updateNote(id:Long, title:String, text:String, time:String,color:Int, state: NoteState) {
        Timber.tag("TTT").d("EditNoteScreenViewModelImpl")
        val image = byteArrayOf()
        val note = NoteEntity(id, title, text, image, color, time, false, state)
        backLiveData.addSourceDisposable(editNoteUseCase.updateNote(note)){}
        backLiveData.value = Unit
    }

}