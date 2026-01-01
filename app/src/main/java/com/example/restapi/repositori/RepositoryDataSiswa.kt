package com.example.restapi.repositori

import com.example.restapi.apiservice.ServiceApiSiswa
import com.example.restapi.modeldata.DataSiswa

interface RepositoryDataSiswa {
    suspend fun getDataSiswa(): List<DataSiswa>
    suspend fun postDataSiswa(dataSiswa: DataSiswa)
    suspend fun updateDataSiswa(dataSiswa: DataSiswa)
    suspend fun deleteDataSiswa(id: Int)
}

class JaringanRepositoryDataSiswa(
    private val serviceApiSiswa: ServiceApiSiswa
) : RepositoryDataSiswa {
    override suspend fun getDataSiswa(): List<DataSiswa> {
        return try {
            serviceApiSiswa.getSiswa()
        } catch (e: Exception) {
            // If JSON parsing fails or response is empty, return empty list
            e.printStackTrace()
            emptyList()
        }
    }


    override suspend fun postDataSiswa(dataSiswa: DataSiswa) {
        try {
            val response = serviceApiSiswa.postSiswa(dataSiswa)
            if (!response.isSuccessful) {
                throw Exception("Insert failed: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}
