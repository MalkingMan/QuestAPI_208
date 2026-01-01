package com.example.restapi.ui.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.restapi.ui.uicontroller.route.DestinasiDetail
import com.example.restapi.ui.uicontroller.route.DestinasiEntry
import com.example.restapi.ui.uicontroller.route.DestinasiHome
import com.example.restapi.ui.view.DetailSiswaScreen
import com.example.restapi.ui.view.EntrySiswaScreen
import com.example.restapi.ui.view.HomeScreen
import com.example.restapi.ui.viewmodel.EntryViewModel
import com.example.restapi.ui.viewmodel.PenyediaViewModel

@Composable
fun DataSiswaApp(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    HostNavigasi(navController = navController, modifier = modifier)
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToDetail = { siswaId ->
                    navController.navigate("${DestinasiDetail.route}/$siswaId")
                }
            )
        }
        composable(DestinasiEntry.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(DestinasiHome.route)
            }
            val entryViewModel: EntryViewModel = viewModel(
                viewModelStoreOwner = parentEntry,
                factory = PenyediaViewModel.Factory
            )

            EntrySiswaScreen(
                navigateBack = {
                    entryViewModel.resetUiState()
                    navController.popBackStack(DestinasiHome.route, inclusive = false)
                },
                viewModel = entryViewModel
            )
        }
        composable(
            route = DestinasiDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetail.SISWA_ID) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(DestinasiHome.route)
            }
            val entryViewModel: EntryViewModel = viewModel(
                viewModelStoreOwner = parentEntry,
                factory = PenyediaViewModel.Factory
            )

            DetailSiswaScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEdit = { siswa ->
                    entryViewModel.loadForEdit(siswa)
                    navController.navigate(DestinasiEntry.route)
                }
            )
        }
    }
}
