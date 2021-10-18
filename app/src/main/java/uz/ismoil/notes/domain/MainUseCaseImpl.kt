package uz.ismoil.notes.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState
import uz.ismoil.notes.models.Note
import uz.ismoil.notes.repository.NoteRepository
import javax.inject.Inject


class MainUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : MainUseCase {

    override fun getAllNotes(): LiveData<List<NoteEntity>> {
        return noteRepository.getAllNotes()
    }

    override fun getNotesByState(state: NoteState): LiveData<List<NoteEntity>> {
        return noteRepository.getNotesByState(state)
    }

    override fun deleteNote(noteEntity: NoteEntity): LiveData<Unit> = liveData(Dispatchers.IO) {
        noteEntity.state = NoteState.TRASH
        noteRepository.updateNote(noteEntity)
        emit(Unit)
    }

    override fun archiveNote(noteEntity: NoteEntity): LiveData<Unit> = liveData (Dispatchers.IO){
        noteEntity.state = NoteState.ARCHIVED
        noteRepository.updateNote(noteEntity)
        emit(Unit)
    }


}