package uz.ismoil.notes.viewmodels.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState
import uz.ismoil.notes.domain.ArchivedUseCase
import uz.ismoil.notes.viewmodels.ArchivedScreenViewModel
import javax.inject.Inject

@HiltViewModel
class ArchivedScreenViewModelImpl @Inject constructor(
    private val archivedUseCase: ArchivedUseCase
) : ArchivedScreenViewModel, ViewModel(){

    override fun getArchiveNotes(): LiveData<List<NoteEntity>> = archivedUseCase.getNotesByState(NoteState.ARCHIVED)


}