package com.example.sigit_moonlight.pertemuan_4.main

import android.app.Application
import androidx.lifecycle.*
import com.example.sigit_moonlight.data.database.NusaDataDatabase
import com.example.sigit_moonlight.data.database.PendudukEntity
import com.example.sigit_moonlight.data.repository.PendudukRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PendudukViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PendudukRepository
    val pendudukCount: LiveData<Int>

    private val _searchQuery = MutableLiveData<String>("")
    val allPenduduk: LiveData<List<PendudukEntity>>

    init {
        val database = NusaDataDatabase.getDatabase(application)
        repository = PendudukRepository(database.pendudukDao())
        pendudukCount = repository.pendudukCount
        
        allPenduduk = _searchQuery.switchMap { query ->
            if (query.isNullOrEmpty()) {
                repository.allPenduduk
            } else {
                repository.searchPenduduk(query)
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun insert(penduduk: PendudukEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(penduduk)
    }

    fun update(penduduk: PendudukEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(penduduk)
    }

    fun delete(penduduk: PendudukEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(penduduk)
    }
}
