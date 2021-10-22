package uz.ismoil.notes.viewmodels.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState
import uz.ismoil.notes.domain.MainUseCase
import uz.ismoil.notes.utils.addSourceDisposable
import uz.ismoil.notes.viewmodels.MainScreenViewModel
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModelImpl @Inject constructor(
    private val useCase: MainUseCase
) : MainScreenViewModel, ViewModel() {

    override val openCreateScreenLiveData = MutableLiveData<Unit>()
    override val openReadeScreenLiveData = MediatorLiveData<NoteEntity>()

    override fun openCreateScreen() {
        openCreateScreenLiveData.value = Unit

    }

    override fun openReadeScreen(note:NoteEntity) {
        openReadeScreenLiveData.value = note
    }

    override fun getAllNotes(): LiveData<List<NoteEntity>> = useCase.getAllNotes()

    override fun getActiveNotes():LiveData<List<NoteEntity>> = useCase.getNotesByState(NoteState.ACTIVE)

    override fun deleteNote(note: NoteEntity) {
        Timber.tag("TTT").d("MainScreenViewModelImpl 1")
        openReadeScreenLiveData.addSourceDisposable(useCase.deleteNote(note)){
            Timber.tag("TTT").d("MainScreenViewModelImpl 2")
        }
    }

    override fun archiveNote(note: NoteEntity) {
        Timber.tag("TTT").d("MainScreenViewModelImpl 1")
        openReadeScreenLiveData.addSourceDisposable(useCase.archiveNote(note)){
            Timber.tag("TTT").d("MainScreenViewModelImpl 2")
        }
    }

    override fun searchDatabase(searchQuery: String): LiveData<List<NoteEntity>> {
        return useCase.searchDatabase(searchQuery)
    }
}