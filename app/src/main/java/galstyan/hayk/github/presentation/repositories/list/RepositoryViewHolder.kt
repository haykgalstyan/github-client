package galstyan.hayk.github.presentation.repositories.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import galstyan.hayk.github.databinding.ItemRepositoryBinding
import galstyan.hayk.github.domain.model.Repository

class RepositoryViewHolder(
    private val binding: ItemRepositoryBinding,
    private val onClick: (Repository) -> Unit
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    init {
        binding.root.setOnClickListener(this)
    }

    fun bind(repository: Repository) {
        binding.root.tag = repository
        binding.title.text = repository.name
        binding.description.text = repository.description
        binding.stars.text = repository.stars.toString()
    }

    override fun onClick(v: View?) {
        onClick(v?.tag as Repository)
    }
}