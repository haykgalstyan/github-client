package galstyan.hayk.github.presentation.repositories.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import galstyan.hayk.github.domain.model.Repository
import galstyan.hayk.github.domain.usecase.GetRepositoryList
import galstyan.hayk.github.presentation.error.UiError
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    private val getRepositoryList: GetRepositoryList,
) : ViewModel() {

    private val _uiStateObservable =
        MutableLiveData<RepositoryListUiState>(RepositoryListUiState.Loading)
    val uiStateObservable = _uiStateObservable as LiveData<RepositoryListUiState>

    private var page = 0

    private val _repositoryList = ArrayList<Repository>()
    val repositoryList get() = _repositoryList as List<Repository>

    fun onListScrolledTo(position: Int) {
        val didScrollEnough = position > _repositoryList.size / 2
        val isLoading = _uiStateObservable.value is RepositoryListUiState.Loading
        if (didScrollEnough && !isLoading) {
            loadRepositories()
        }
    }

    init {
        loadRepositories()
    }

    private fun loadRepositories() {
        _uiStateObservable.value = RepositoryListUiState.Loading
        page++
        viewModelScope.launch {
            _uiStateObservable.value = try {
                val pageList = getRepositoryList(page)
                val pageSize = pageList.size
                val from = page * pageSize - pageSize
                val to = page * pageSize
                _repositoryList.addAll(pageList)
                RepositoryListUiState.Data(RepositoryListUiState.Data.ListData(_repositoryList, from, to))
            } catch (e: Exception) {
                RepositoryListUiState.Error(UiError.from(e))
            }
        }
    }
}