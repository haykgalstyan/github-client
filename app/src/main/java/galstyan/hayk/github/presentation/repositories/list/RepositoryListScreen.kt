package galstyan.hayk.github.presentation.repositories.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import galstyan.hayk.github.R
import galstyan.hayk.github.databinding.ItemRepositoryBinding
import galstyan.hayk.github.databinding.ScreenRepositoryListBinding
import galstyan.hayk.github.domain.model.Repository
import galstyan.hayk.github.presentation.util.viewBinding


@AndroidEntryPoint
class RepositoryListScreen : Fragment(R.layout.screen_repository_list) {

    private val vm by viewModels<RepositoryListViewModel>()
    private val vb by viewBinding(ScreenRepositoryListBinding::bind)

    private val onRepositoryClick: (Repository) -> Unit = {
        findNavController().navigate(RepositoryListScreenDirections.toRepositoryDetailsScreen(it.id))
    }

    private val listAdapter = object : RecyclerView.Adapter<RepositoryViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RepositoryViewHolder(
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onRepositoryClick
        )

        override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) = holder.bind(
            vm.repositoryList[position]
        )

        override fun getItemCount() = vm.repositoryList.size
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.playerList.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addScrollListener(this)
            this.adapter = listAdapter
        }
        vm.uiStateObservable.observe(viewLifecycleOwner) {
            vb.loading.isVisible = false
            when (it) {
                is RepositoryListUiState.Loading -> vb.loading.isVisible = true
                is RepositoryListUiState.Data -> showData(it)
                is RepositoryListUiState.Error -> showError(it)
            }
        }
    }

    private fun showData(data: RepositoryListUiState.Data) {
        listAdapter.notifyItemRangeInserted(data.listData.from, data.listData.to)
    }

    private fun showError(error: RepositoryListUiState.Error) {
        Toast.makeText(context, error.uiError.errorRes, Toast.LENGTH_LONG).show()
    }


    private fun addScrollListener(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisiblePosition =
                    linearLayoutManager.findLastCompletelyVisibleItemPosition()
                vm.onListScrolledTo(lastVisiblePosition)
            }
        })
    }
}