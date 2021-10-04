package br.com.dio.businesscard.data.repository

import br.com.dio.businesscard.data.roomDB.BusinessCardDao
import br.com.dio.businesscard.data.model.BusinessCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BusinessCardRepository(private val dao: BusinessCardDao) {

    fun insert(businessCard: BusinessCard) = runBlocking {
        launch(Dispatchers.IO) {
            dao.insert(businessCard)
        }
    }

    fun getAll() = dao.getAll()

    fun searchDatabase(searchQuery: String): Flow<List<BusinessCard>> {
        return dao.searchDatabase(searchQuery)
    }

    fun delete(businessCard: BusinessCard) = runBlocking {
        launch(Dispatchers.IO) {
            dao.delete(businessCard)
        }
    }
}