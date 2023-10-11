package com.example.sololearntesttask.network

import com.example.sololearntesttask.models.GitReposFullDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class GitReposInteractor(private val repository: GitHubRepository) {

    suspend fun getRepositoriesWithDetails() = withContext(Dispatchers.IO) {

        val result = repository.getRepositories()
        val data = result.data

       return@withContext if (result is Success && !data.isNullOrEmpty()) {
            data.map {
                async {
                    val detailsInfoResult =
                        repository.getRepositoriesByName(it.owner.login, it.name)
                    if (detailsInfoResult is Success) {
                        GitReposFullDTO(it, detailsInfoResult.data!!)
                    } else {
                        throw Exception(detailsInfoResult.error)
                    }
                }
            }.awaitAll()
        } else {
            throw Exception(result.error)

        }


    }

}