package galstyan.hayk.github.presentation.repositories.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import galstyan.hayk.github.domain.usecase.GetRepositoryDetails
import galstyan.hayk.github.presentation.error.UiError
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RepositoryDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRepositoryDetails: GetRepositoryDetails,
) : ViewModel() {

    private val repositoryId =
        RepositoryDetailsScreenArgs.fromSavedStateHandle(savedStateHandle).repositoryId

    private val _uiStateObservable =
        MutableLiveData<RepositoryDetailsUiState>(RepositoryDetailsUiState.Loading)
    val uiStateObservable = _uiStateObservable as LiveData<RepositoryDetailsUiState>


    init {
        viewModelScope.launch {
            _uiStateObservable.value = try {
                RepositoryDetailsUiState.Data(getRepositoryDetails(repositoryId))
            } catch (e: Exception) {
                RepositoryDetailsUiState.Error(UiError.from(e))
            }
        }
    }
}