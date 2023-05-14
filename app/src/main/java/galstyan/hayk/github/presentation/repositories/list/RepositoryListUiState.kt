package galstyan.hayk.github.presentation.repositories.list

import galstyan.hayk.github.domain.model.Repository
import galstyan.hayk.github.presentation.error.UiError

sealed class RepositoryListUiState {
    object Loading : RepositoryListUiState()
    data class Error(val uiError: UiError) : RepositoryListUiState()
    data class Data(val listData: ListData) : RepositoryListUiState() {
        data class ListData(
            val repositories: List<Repository>,
            val from: Int,
            val to: Int
        )
    }
}

