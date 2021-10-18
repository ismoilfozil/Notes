package uz.ismoil.notes.data.daos

import androidx.room.*

@Dao
interface BaseDao<T> {

    @Delete
    suspend fun delete(data: T): Int

    @Delete
    suspend fun deleteAll(data: List<T>): Int

    @Update
    suspend fun update(data: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<T>)


}