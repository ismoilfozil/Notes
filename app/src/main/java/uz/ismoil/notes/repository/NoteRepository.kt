package uz.ismoil.notes.repository

import android.util.Log
import androidx.lifecycle.LiveData
import uz.ismoil.notes.data.daos.NoteDao
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    suspend fun addNote(note: NoteEntity): Long {
        return noteDao.insert(note)
    }

    suspend fun deleteNote(note: NoteEntity) {
        noteDao.delete(note)
    }

    suspend fun updateNote(note: NoteEntity){
        noteDao.update(note)
    }

    fun getAllNotes(): LiveData<List<NoteEntity>> {
        Log.d("TTT", "getAllNotes: ")
        return noteDao.getAll()
    }

    fun getNotesByState(state: NoteState):LiveData<List<NoteEntity>>{
        return noteDao.getByState(state)
    }
}