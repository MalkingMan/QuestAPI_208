package com.example.restapi.repositori

import com.example.restapi.apiservice.ServiceApiSiswa
import com.example.restapi.modeldata.DataSiswa

interface RepositoryDataSiswa {
    suspend fun getDataSiswa(): List<DataSiswa>
    suspend fun postDataSiswa(dataSiswa: DataSiswa)
    // suspend fun getSatuSiswa(id: Int): DataSiswa
    // suspend fun editSatuSiswa(id: Int, dataSiswa: DataSiswa)
    // suspend fun hapusSatuSiswa(id: Int)
}

class JaringanRepositoryDataSiswa(
    private val serviceApiSiswa: ServiceApiSiswa
) : RepositoryDataSiswa {
    override suspend fun getDataSiswa(): List<DataSiswa> = serviceApiSiswa.getSiswa()
    override suspend fun postDataSiswa(dataSiswa: DataSiswa) = serviceApiSiswa.postSiswa(dataSiswa)
}
