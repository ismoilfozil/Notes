package uz.ismoil.notes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.ismoil.notes.data.daos.NoteDao
import uz.ismoil.notes.data.entities.NoteEntity

@Database(
    entities = [
        NoteEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

}