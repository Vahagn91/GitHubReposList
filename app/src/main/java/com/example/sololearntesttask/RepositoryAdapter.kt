package com.example.sololearntesttask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sololearntesttask.databinding.ItemRepositoryBinding
import okhttp3.internal.notifyAll

class RepositoryAdapter(
    private val onClicked: (String?, String?, Int?) -> Unit
) :
    RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    private  var repositories : List<RepositoryListDTO?>? = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = repositories?.get(position)
        repository.let { it?.let { it1 -> holder.bind(it1) } }
    }

    override fun getItemCount(): Int {
        return repositories?.size!!
    }

    fun getItem(position: Int): RepositoryListDTO {
        return repositories?.get(position)!!
    }

    fun getList(): List<RepositoryListDTO?>? {
        return repositories
    }

    fun updateList(list: List<RepositoryListDTO?>?){
        repositories = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repositoryListDTO: RepositoryListDTO) {

            val avatarUrl = repositoryListDTO.owner.avatar_url

            Glide.with(binding.root.context)
                .load(avatarUrl)
//                .placeholder(R.drawable.placeholder_image) // Optional placeholder image
//                .error(R.drawable.error_image) // Optional error image
                .into(binding.avatarImageView)

            binding.textName.text = repositoryListDTO.name // Access views using View Binding
            binding.textDescription.text = repositoryListDTO.description
            binding.starCount.text = repositoryListDTO.stargazers_count.toString()
            itemView.setOnClickListener {
                onClicked(repositoryListDTO.owner.login, repositoryListDTO.name, position)
            }
        }
    }
}