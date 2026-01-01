package com.example.restapi.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restapi.modeldata.DataSiswa
import com.example.restapi.modeldata.DetailSiswa
import com.example.restapi.repositori.RepositoryDataSiswa
import com.example.restapi.ui.uicontroller.route.DestinasiDetail
import kotlinx.coroutines.launch

sealed interface DetailUiState {
    data class Success(val siswa: DetailSiswa) : DetailUiState
    object Error : DetailUiState
    object Loading : DetailUiState
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {

    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private val siswaId: Int = checkNotNull(savedStateHandle[DestinasiDetail.SISWA_ID])

    init {
        getSiswaById()
    }

fun getSiswaById() {
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            detailUiState = try {
                val allSiswa = repositoryDataSiswa.getDataSiswa()
                val siswa = allSiswa.find { it.id == siswaId }
                if (siswa != null) {
                    DetailUiState.Success(
                        DetailSiswa(
                            id = siswa.id ?: 0,
                            nama = siswa.nama,
                            alamat = siswa.alamat,
                            telpon = siswa.telpon
                        )
                    )
                } else {
                    DetailUiState.Error
                }