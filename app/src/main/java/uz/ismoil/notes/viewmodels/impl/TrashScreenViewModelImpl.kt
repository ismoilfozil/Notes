package uz.ismoil.notes.viewmodels.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState
import uz.ismoil.notes.domain.TrashUseCase
import uz.ismoil.notes.utils.addSourceDisposable
import uz.ismoil.notes.viewmodels.TrashScreenViewModel
import javax.inject.Inject

@HiltViewModel
class TrashScreenViewModelImpl @Inject constructor(
    private val trashUseCase: TrashUseCase
) : TrashScreenViewModel, ViewModel() {
    override val backLiveData = MediatorLiveData<Unit>()


    override fun getTrashNotes(): LiveData<List<NoteEntity>> =
        trashUseCase.getNotesByState(NoteState.TRASH)


    override fun deleteAll() {
        backLiveData.addSourceDisposable(trashUseCase.deleteALl()){}
    }

    override fun restore(data:NoteEntity) {
         backLiveData.addSourceDisposable(trashUseCase.restore(data)){}
    }

    override fun delete(data:NoteEntity) {
        Timber.tag("TTT").d("TrashScreenViewModelImpl = $data")
        backLiveData.addSourceDisposable(trashUseCase.delete(data)){}
    }

    override fun searchDatabase(searchQuery: String): LiveData<List<NoteEntity>> {
        return trashUseCase.searchDatabase(searchQuery)
    }

}