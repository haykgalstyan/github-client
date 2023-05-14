package galstyan.hayk.github.presentation.repositories.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import galstyan.hayk.github.R
import galstyan.hayk.github.databinding.ScreenRepositoryDetailsBinding
import galstyan.hayk.github.presentation.util.viewBinding


@AndroidEntryPoint
class RepositoryDetailsScreen : Fragment(R.layout.screen_repository_details) {

    private val vm by viewModels<RepositoryDetailsViewModel>()
    private val vb by viewBinding(ScreenRepositoryDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.uiStateObservable.observe(viewLifecycleOwner) {
            vb.loading.isVisible = false
            when (it) {
                is RepositoryDetailsUiState.Loading -> vb.loading.isVisible = true
                is RepositoryDetailsUiState.Data -> showData(it)
                is RepositoryDetailsUiState.Error -> showError(it)
            }
        }
    }

    private fun showData(data: RepositoryDetailsUiState.Data) {
        val repository = data.repository
        vb.title.text = repository.name
        vb.description.text = repository.description
        vb.stars.text = repository.stars.toString()
        vb.forks.text = repository.forks.toString()
        vb.language.text = repository.language
        vb.link.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(repository.link)))
        }
    }

    private fun showError(error: RepositoryDetailsUiState.Error) {
        Toast.makeText(context, error.uiError.errorRes, Toast.LENGTH_SHORT).show()
    }
}