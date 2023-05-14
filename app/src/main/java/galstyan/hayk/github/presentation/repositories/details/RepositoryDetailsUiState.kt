package galstyan.hayk.github.presentation.repositories.details

import galstyan.hayk.github.domain.model.Repository
import galstyan.hayk.github.presentation.error.UiError

sealed class RepositoryDetailsUiState {
    object Loading : RepositoryDetailsUiState()
    data class Error(val uiError: UiError) : RepositoryDetailsUiState()
    data class Data(val repository: Repository) : RepositoryDetailsUiState()
}