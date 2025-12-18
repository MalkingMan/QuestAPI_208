package com.example.restapi.apiservice

import com.example.restapi.modeldata.DataSiswa
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ServiceApiSiswa {
    @GET("bacaTeman.php")
    suspend fun getSiswa(): List<DataSiswa>

    @POST("insertTM.php")
    suspend fun postSiswa(@Body dataSiswa: DataSiswa)

    // @GET("baca1Teman.php/{id}")
    // suspend fun getSatuSiswa(@Query("id") id: Int)

    // @PUT("editTM.php/{id}")
    // suspend fun editSatuSiswa(@Query("id") id: Int)
}
