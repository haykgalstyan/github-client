package galstyan.hayk.github.presentation.repositories.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import galstyan.hayk.github.domain.usecase.GetRepositoryDetails
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RepositoryDetailsViewModel @Inject constructor(
    private val getRepositoryDetails: GetRepositoryDetails,
) : ViewModel() {

    init {
        viewModelScope.launch { getRepositoryDetails() }
    }

}