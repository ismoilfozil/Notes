package uz.ismoil.notes.viewmodels.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState
import uz.ismoil.notes.domain.TrashUseCase
import uz.ismoil.notes.viewmodels.TrashScreenViewModel
import javax.inject.Inject

@HiltViewModel
class TrashScreenViewModelImpl @Inject constructor(
    private val trashUseCase: TrashUseCase
): TrashScreenViewModel, ViewModel(){

    override fun getTrashNotes(): LiveData<List<NoteEntity>> = trashUseCase.getNotesByState(NoteState.TRASH)

}