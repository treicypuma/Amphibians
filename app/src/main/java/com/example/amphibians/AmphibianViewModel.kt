package com.example.amphibians

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

sealed interface AmphibianUiState {
    data class Success(val amphibians: List<Amphibian>) : AmphibianUiState
    data object Error : AmphibianUiState
    data object Loading : AmphibianUiState
}

class AmphibianViewModel : ViewModel() {

    private val repositorio = AmphibianRepository()

    var uiState: AmphibianUiState by mutableStateOf(AmphibianUiState.Loading)
        private set

    init {
        getAmphibians()
    }

    fun getAmphibians() {
        viewModelScope.launch {
            uiState = AmphibianUiState.Loading
            uiState = try {
                AmphibianUiState.Success(repositorio.getAmphibians())
            } catch (e: Exception) {
                AmphibianUiState.Error
            }
        }
    }
}