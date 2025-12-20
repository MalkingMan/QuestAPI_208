package com.example.restapi.modeldata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataSiswa(
    @SerialName("id")
    val id: Int? = null,  // Nullable untuk POST request
    @SerialName("nama")
    val nama: String = "",
    @SerialName("alamat")
    val alamat: String = "",
    @SerialName("telpon")
    val telpon: String = ""
)

@Serializable
data class SiswaResponse(
    @SerialName("data")
    val data: List<DataSiswa>? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("message")
    val message: String? = null
)

data class UIStateSiswa(
    val detailSiswa: DetailSiswa = DetailSiswa(),
    val isEntryValid: Boolean = false
)

data class DetailSiswa(
    val id: Int = 0,
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = ""
)

fun DetailSiswa.toDataSiswa(): DataSiswa = DataSiswa(
    id = null,  // Jangan kirim id untuk data baru, biarkan database auto-generate
    nama = nama,
    alamat = alamat,
    telpon = telpon
)

fun DataSiswa.toDetailSiswa(): DetailSiswa = DetailSiswa(
    id = id ?: 0,  // Gunakan 0 jika id null
    nama = nama,
    alamat = alamat,
    telpon = telpon
)

fun DataSiswa.toUIStateSiswa(isEntryValid: Boolean = false): UIStateSiswa = UIStateSiswa(
    detailSiswa = this.toDetailSiswa(),
    isEntryValid = isEntryValid
)