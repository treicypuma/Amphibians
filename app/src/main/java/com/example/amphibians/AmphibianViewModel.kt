package com.example.amphibians

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch

sealed interface AmphibianUiState {
    data class Success(val amphibians: List<Amphibian>) : AmphibianUiState
    data object Error : AmphibianUiState
    data object Loading : AmphibianUiState
}

class AmphibianViewModel(
    private val repositorio: AmphibianRepository
) : ViewModel() {

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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AmphibiansApplication
                AmphibianViewModel(aplicacion.container.amphibianRepository)
            }
        }
    }
}