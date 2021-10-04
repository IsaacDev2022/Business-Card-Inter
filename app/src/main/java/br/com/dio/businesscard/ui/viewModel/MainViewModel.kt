package br.com.dio.businesscard.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import br.com.dio.businesscard.data.model.BusinessCard
import br.com.dio.businesscard.data.repository.BusinessCardRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val businessCardRepository: BusinessCardRepository): ViewModel() {

    fun insert(businessCard: BusinessCard) {
        businessCardRepository.insert(businessCard)
    }

    fun getAll(): LiveData<List<BusinessCard>> {
        return businessCardRepository.getAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<BusinessCard>> {
        return businessCardRepository.searchDatabase(searchQuery).asLiveData()
    }

    fun delete(businessCard: BusinessCard) {
        businessCardRepository.delete(businessCard)
    }
}

class MainViewModelFactory(private val repository: BusinessCardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}