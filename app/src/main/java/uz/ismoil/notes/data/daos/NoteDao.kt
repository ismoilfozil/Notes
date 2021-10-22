package uz.ismoil.notes.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState

@Dao
interface NoteDao : BaseDao<NoteEntity> {

    @Query("SELECT * FROM NoteEntity")
    fun getAll():LiveData<List<NoteEntity>>

    @Query("SELECT *FROM NoteEntity WHERE state =:state")
    fun getByState(state:NoteState):LiveData<List<NoteEntity>>

    @Query("DELETE FROM NoteEntity WHERE state =:state")
    suspend fun deleteAllTrashNotes(state: NoteState)

    @Query("SELECT * FROM NoteEntity WHERE state=:state AND (title LIKE :searchQuery OR text LIKE :searchQuery)")
    fun searchDatabase(searchQuery: String, state: NoteState): LiveData<List<NoteEntity>>

}