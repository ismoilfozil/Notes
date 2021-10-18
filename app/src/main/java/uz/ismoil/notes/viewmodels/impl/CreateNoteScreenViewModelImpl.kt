package uz.ismoil.notes.viewmodels.impl

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.ismoil.notes.utils.addSourceDisposable
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState
import uz.ismoil.notes.domain.CreateNoteUseCase
import uz.ismoil.notes.viewmodels.CreateNoteScreenViewModel
import javax.inject.Inject

@HiltViewModel
class CreateNoteScreenViewModelImpl @Inject constructor(
    private val createNoteUseCase: CreateNoteUseCase
) : CreateNoteScreenViewModel, ViewModel() {
    override val backLiveData = MediatorLiveData<Unit>()

    override fun addNote(title: String, text: String, color: Int, state: NoteState) {
        val image = byteArrayOf()
        val note = NoteEntity(0, title, text, image, color, System.currentTimeMillis(), false, state)

        backLiveData.addSourceDisposable(createNoteUseCase.addNote(note)) {
            backLiveData.value = Unit
        }
    }


}
