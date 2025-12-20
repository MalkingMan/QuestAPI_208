package com.example.restapi.apiservice

import com.example.restapi.modeldata.DataSiswa
import retrofit2.Response // Pastikan import ini benar
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers // Opsional, tapi bagus untuk memastikan JSON
import retrofit2.http.POST

interface ServiceApiSiswa {

    @Headers("Accept: application/json")
    @GET("bacateman.php")
    suspend fun getSiswa(): List<DataSiswa>

    @Headers("Content-Type: application/json")
    @POST("insertTM.php")
    suspend fun postSiswa(
        @Body dataSiswa: DataSiswa
    ): Response<Void> // Gunakan Void jika tidak butuh memparsing return body, atau buat data class ResponseSiswa
}
