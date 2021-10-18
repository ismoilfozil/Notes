package uz.ismoil.notes.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.repository.NoteRepository
import javax.inject.Inject

class EditNoteUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : EditNoteUseCase {

    override fun updateNote(noteEntity: NoteEntity): LiveData<Long>  = liveData(Dispatchers.IO){
       noteRepository.updateNote(noteEntity)
    }


}