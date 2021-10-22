package uz.ismoil.notes.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState
import uz.ismoil.notes.repository.NoteRepository
import javax.inject.Inject

class ArchivedUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : ArchivedUseCase {

    override fun getNotesByState(state: NoteState): LiveData<List<NoteEntity>> {
        return noteRepository.getNotesByState(state)
    }

    override fun searchDatabase(searchQuery: String): LiveData<List<NoteEntity>> {
        val state = NoteState.ARCHIVED
        return noteRepository.searchDatabase(searchQuery, state)
    }

    override fun unarchive(data: NoteEntity): LiveData<Unit> = liveData (Dispatchers.IO){
        data.state = NoteState.ACTIVE
        noteRepository.updateNote(data)
    }

    override fun delete(data: NoteEntity): LiveData<Unit> = liveData(Dispatchers.IO){
        data.state = NoteState.TRASH
        noteRepository.updateNote(data)
    }

}