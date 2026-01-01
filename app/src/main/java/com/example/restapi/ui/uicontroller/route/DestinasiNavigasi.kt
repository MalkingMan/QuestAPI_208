package com.example.restapi.ui.uicontroller.route

import com.example.restapi.R

interface DestinasiNavigasi {
    val route: String
    val titleRes: Int
}

object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = R.string.app_name
}

object DestinasiEntry : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = R.string.entry_siswa
}

object DestinasiDetail : DestinasiNavigasi {
    override val route = "item_detail"
    override val titleRes = R.string.detail_siswa
    const val SISWA_ID = "siswaId"
    val routeWithArgs = "$route/{$SISWA_ID}"
}

