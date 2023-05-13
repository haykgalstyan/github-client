package galstyan.hayk.github.presentation.repositories.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import galstyan.hayk.github.domain.usecase.GetRepositoryList
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    private val getRepositoryList: GetRepositoryList,
) : ViewModel() {

    init {
        viewModelScope.launch { getRepositoryList() }
    }

}