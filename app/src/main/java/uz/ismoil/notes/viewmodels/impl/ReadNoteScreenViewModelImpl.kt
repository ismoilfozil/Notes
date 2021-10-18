package uz.ismoil.notes.viewmodels.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.viewmodels.ReadNoteScreenViewModel


class ReadNoteScreenViewModelImpl : ReadNoteScreenViewModel,ViewModel() {
    override val openEditScreenLiveData = MutableLiveData<NoteEntity>()

    override fun openEditScreen(noteEntity: NoteEntity) {
        openEditScreenLiveData.value = noteEntity
    }

}