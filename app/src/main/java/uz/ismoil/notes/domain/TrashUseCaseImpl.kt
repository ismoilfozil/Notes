package uz.ismoil.notes.domain

import androidx.lifecycle.LiveData
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

}