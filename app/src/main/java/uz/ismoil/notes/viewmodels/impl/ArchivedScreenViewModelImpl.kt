package uz.ismoil.notes.viewmodels.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState
import uz.ismoil.notes.domain.ArchivedUseCase
import uz.ismoil.notes.utils.addSourceDisposable
import uz.ismoil.notes.viewmodels.ArchivedScreenViewModel
import javax.inject.Inject

@HiltViewModel
 class ArchivedScreenViewModelImpl @Inject constructor(
    private val archivedUseCase: ArchivedUseCase
) : ArchivedScreenViewModel, ViewModel(){
    override val backLiveData = MediatorLiveData<Unit>()


    override fun getArchiveNotes(): LiveData<List<NoteEntity>> = archivedUseCase.getNotesByState(NoteState.ARCHIVED)

    override fun delete(data: NoteEntity) {
        backLiveData.addSourceDisposable(archivedUseCase.delete(data)){}
    }

    override fun unarchive(data: NoteEntity) {
        backLiveData.addSourceDisposable(archivedUseCase.unarchive(data)){}
    }

    override fun searchDatabase(searchQuery: String): LiveData<List<NoteEntity>> {
        return archivedUseCase.searchDatabase(searchQuery)
    }


}