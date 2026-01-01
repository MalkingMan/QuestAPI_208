package com.example.restapi.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restapi.modeldata.DetailSiswa
import com.example.restapi.ui.viewmodel.DetailUiState
import com.example.restapi.ui.viewmodel.DetailViewModel
import com.example.restapi.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSiswaScreen(
    navigateBack: () -> Unit,
    navigateToEdit: (DetailSiswa) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var showDeleteDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detail Siswa") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (viewModel.detailUiState is DetailUiState.Success) {
                        val siswa = (viewModel.detailUiState as DetailUiState.Success).siswa
                        navigateToEdit(siswa)
                    }
                },
                containerColor = Color(0xFFE6D5F5),
                contentColor = Color(0xFF5D4A7A),
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        DetailBody(
            detailUiState = viewModel.detailUiState,
            onDeleteClick = { showDeleteDialog = true },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Perhatian") },
                text = { Text("Apakah anda yakin menghapus ini ?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                            coroutineScope.launch {
                                try {
                                    viewModel.deleteSiswa()
                                    snackbarHostState.showSnackbar("Data berhasil dihapus")
                                    navigateBack()
                                } catch (e: Exception) {
                                    snackbarHostState.showSnackbar("Gagal menghapus data: ${e.message}")
                                }
                            }
                        }
                    ) {
                        Text("Ya")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Tidak")
                    }
                }
            )
        }
    }
}

@Composable
private fun DetailBody(
    detailUiState: DetailUiState,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (detailUiState) {
        is DetailUiState.Loading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is DetailUiState.Success -> {
            DetailContent(
                siswa = detailUiState.siswa,
                onDeleteClick = onDeleteClick,
                modifier = modifier
            )
        }
        is DetailUiState.Error -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Text("Error loading data")
            }
        }
    }
}

@Composable
private fun DetailContent(
    siswa: DetailSiswa,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Card dengan background ungu muda
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE6D5F5)
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DetailRow(label = "Nama Siswa*", value = siswa.nama)
                DetailRow(label = "Alamat Siswa*", value = siswa.alamat)
                DetailRow(label = "Telpon Siswa*", value = siswa.telpon)
            }
        }

        // Tombol Hapus dengan outline style
        OutlinedButton(
            onClick = onDeleteClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF5D4A7A)
            )
        ) {
            Text("Hapus")
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF5D4A7A)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF5D4A7A)
        )
    }
}

