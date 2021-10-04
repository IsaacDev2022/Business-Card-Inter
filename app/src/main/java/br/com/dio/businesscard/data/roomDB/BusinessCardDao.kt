package br.com.dio.businesscard.data.roomDB

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.dio.businesscard.data.model.BusinessCard
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessCardDao {

    @Query("SELECT * FROM BusinessCard")
    fun getAll(): LiveData<List<BusinessCard>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(businessCard: BusinessCard)

    @Query("SELECT * FROM BusinessCard WHERE nome LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<BusinessCard>>

    @Delete
    suspend fun delete(businessCard: BusinessCard)
}