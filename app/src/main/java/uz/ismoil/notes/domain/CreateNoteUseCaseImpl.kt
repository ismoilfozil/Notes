package uz.ismoil.notes.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.repository.NoteRepository
import javax.inject.Inject

class CreateNoteUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : CreateNoteUseCase {

    override fun addNote(noteEntity: NoteEntity)  = liveData(Dispatchers.IO) {
        val id = noteRepository.addNote(noteEntity)
        emit(id)
    }


}