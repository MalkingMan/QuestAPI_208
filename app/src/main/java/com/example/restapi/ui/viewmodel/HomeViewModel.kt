package com.example.restapi.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restapi.modeldata.DataSiswa
import com.example.restapi.repositori.RepositoryDataSiswa
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException

sealed interface StatusUiSiswa {
    data class Success(val siswa: List<DataSiswa>) : StatusUiSiswa
    data class Error(val message: String? = null) : StatusUiSiswa
    object Loading : StatusUiSiswa
}

class HomeViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel() {
    var siswaUiState: StatusUiSiswa by mutableStateOf(StatusUiSiswa.Loading)
        private set

    init {
        getSiswa()
    }

    fun getSiswa() {
        viewModelScope.launch {
            siswaUiState = StatusUiSiswa.Loading
            siswaUiState = try {
                StatusUiSiswa.Success(repositoryDataSiswa.getDataSiswa())
            } catch (e: IOException) {
                StatusUiSiswa.Error(e.message)
            } catch (e: HttpException) {
                StatusUiSiswa.Error
            }
        }
    }
}
