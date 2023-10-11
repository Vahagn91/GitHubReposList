package com.example.sololearntesttask.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sololearntesttask.R
import com.example.sololearntesttask.databinding.ItemRepositoryBinding
import com.example.sololearntesttask.models.GitReposFullDTO
import com.example.sololearntesttask.models.RepositoryDetailsDTO


class RepositoryAdapter(
    private val onClicked: (RepositoryDetailsDTO) -> Unit
) :
    RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    private var repositories: List<GitReposFullDTO> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    fun getItem(position: Int): GitReposFullDTO {
        return repositories[position]
    }

    fun getList(): List<GitReposFullDTO> {
        return repositories
    }

    fun updateList(list: List<GitReposFullDTO>) {

        val diffResult = calculateDiff(list)

        repositories = list
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repositoryDTO: GitReposFullDTO) {

            val avatarUrl = repositoryDTO.gitRepoDTO.owner.avatarUrl

            Glide.with(binding.root.context)
                .load(avatarUrl)
                .placeholder(R.drawable.ic_person)
                .circleCrop()
                .into(binding.avatarImageView)

            binding.textName.text = repositoryDTO.gitRepoDTO.name // Access views using View Binding
            binding.textDescription.text = repositoryDTO.gitRepoDTO.description
            binding.starCount.text = repositoryDTO.gitRepoDetailDTO.stargazersCount.toString()
            itemView.setOnClickListener {
                onClicked(repositoryDTO.gitRepoDetailDTO)
            }
        }
    }

    private fun calculateDiff(newItems: List<GitReposFullDTO>) =
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun getOldListSize() = repositories.size

            override fun getNewListSize() = newItems.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return repositories[oldItemPosition] == newItems[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val newProduct = newItems[newItemPosition]
                val oldProduct = repositories[oldItemPosition]
                return newProduct.gitRepoDTO == oldProduct.gitRepoDTO
                        && newProduct.gitRepoDetailDTO == oldProduct.gitRepoDetailDTO
            }
        })
}