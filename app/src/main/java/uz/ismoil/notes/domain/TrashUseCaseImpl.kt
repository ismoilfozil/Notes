package uz.ismoil.notes.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState
import uz.ismoil.notes.repository.NoteRepository
import javax.inject.Inject

class TrashUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : TrashUseCase{
    override fun getNotesByState(state: NoteState): LiveData<List<NoteEntity>> {
        return noteRepository.getNotesByState(state)
    }

    override fun deleteALl(): LiveData<Unit>  = liveData(Dispatchers.IO){
        noteRepository.deleteAllTrashNotes()
        emit(Unit)
    }

    override fun delete(data: NoteEntity) : LiveData<Unit> = liveData(Dispatchers.IO){
        Timber.tag("TTT").d("TrashUseCaseImpl = $data")
        noteRepository.deleteNote(data)
        emit(Unit)
    }

    override fun restore(data: NoteEntity):LiveData<Unit> = liveData (Dispatchers.IO){
        data.state = NoteState.ACTIVE
        noteRepository.updateNote(data)
        emit(Unit)
    }

    override fun searchDatabase(searchQuery: String): LiveData<List<NoteEntity>> {
        val state = NoteState.TRASH
        return noteRepository.searchDatabase(searchQuery, state)
    }

}