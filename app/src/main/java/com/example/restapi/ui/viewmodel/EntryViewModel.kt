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

    // status of last network operation: null = idle, true = success, false = failed
    var lastOperationSuccess by mutableStateOf<Boolean?>(null)
        private set

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    fun loadForEdit(detail: DetailSiswa) {
        // prepare UI state for editing existing entry
        uiStateSiswa = UIStateSiswa(detailSiswa = detail, isEntryValid = validasiInput(detail))
    }

    fun clearLastOperation() {
        lastOperationSuccess = null
    }

    fun resetUiState() {
        uiStateSiswa = UIStateSiswa()
        lastOperationSuccess = null
    }

    fun saveSiswa() {
        if (!validasiInput()) return
        viewModelScope.launch {
            try {
                val isEdit = uiStateSiswa.detailSiswa.id > 0
                if (isEdit) {
                    // send with id if editing
                    repositoryDataSiswa.updateDataSiswa(uiStateSiswa.detailSiswa.toDataSiswa())
                } else {
                    repositoryDataSiswa.postDataSiswa(uiStateSiswa.detailSiswa.toDataSiswa())
                }
                lastOperationSuccess = true
            } catch (e: Exception) {
                e.printStackTrace()
                lastOperationSuccess = false
            }
        }
    }
}
