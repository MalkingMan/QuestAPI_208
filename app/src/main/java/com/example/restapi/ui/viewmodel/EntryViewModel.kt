package com.example.restapi.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restapi.modeldata.DetailSiswa
import com.example.restapi.modeldata.UIStateSiswa
import com.example.restapi.modeldata.toDataSiswa
import com.example.restapi.repositori.RepositoryDataSiswa
import kotlinx.coroutines.launch

class EntryViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel() {
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    suspend fun saveSiswa() {
        if (validasiInput()) {
            try {
                repositoryDataSiswa.postDataSiswa(uiStateSiswa.detailSiswa.toDataSiswa())
            } catch (e: Exception) {
                // Log error but don't crash
                e.printStackTrace()
                throw e // Re-throw to let caller know it failed
            }
        }
    }
}
