package com.example.sigit_moonlight.pertemuan_4.main

import android.app.Application
import androidx.lifecycle.*
import com.example.sigit_moonlight.data.database.NusaDataDatabase
import com.example.sigit_moonlight.data.database.AspirasiEntity
import com.example.sigit_moonlight.data.repository.AspirasiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AspirasiViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AspirasiRepository
    val allAspirasi: LiveData<List<AspirasiEntity>>
    val aspirasiCount: LiveData<Int>

    init {
        val database = NusaDataDatabase.getDatabase(application)
        repository = AspirasiRepository(database.aspirasiDao())
        allAspirasi = repository.allAspirasi
        aspirasiCount = repository.aspirasiCount
    }

    fun insert(aspirasi: AspirasiEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(aspirasi)
    }

    fun update(aspirasi: AspirasiEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(aspirasi)
    }

    fun delete(aspirasi: AspirasiEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(aspirasi)
    }
}
