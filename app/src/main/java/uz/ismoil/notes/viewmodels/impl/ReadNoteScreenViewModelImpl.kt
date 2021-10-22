package uz.ismoil.notes.viewmodels.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.domain.MainUseCase
import uz.ismoil.notes.utils.addSourceDisposable
import uz.ismoil.notes.viewmodels.ReadNoteScreenViewModel
import javax.inject.Inject

@HiltViewModel
class ReadNoteScreenViewModelImpl @Inject constructor(
    private val mainUseCase: MainUseCase
) : ReadNoteScreenViewModel,ViewModel() {
    override val openEditScreenLiveData = MutableLiveData<NoteEntity>()
    override val backLiveData = MediatorLiveData<Unit>()

    override fun openEditScreen(noteEntity: NoteEntity) {
        openEditScreenLiveData.value = noteEntity
    }

    override fun delete(data: NoteEntity) {
        backLiveData.addSourceDisposable(mainUseCase.deleteNote(data)){}
    }

    override fun archive(data: NoteEntity) {
        backLiveData.addSourceDisposable(mainUseCase.archiveNote(data)){}
    }

}