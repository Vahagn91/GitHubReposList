package com.example.sololearntesttask.network

import com.example.sololearntesttask.models.RepositoryDTO
import com.example.sololearntesttask.models.RepositoryDetailsDTO


interface GitHubRepository {
    suspend fun getRepositories() : ApiResult<List<RepositoryDTO>?>
    suspend fun getRepositoriesByName(owner : String, proj : String) : ApiResult<RepositoryDetailsDTO>
}

class GitHubRepositoryImpl(private val apiService: GitHubApi) : GitHubRepository {

    override suspend fun getRepositories() : ApiResult<List<RepositoryDTO>?> {
       return coroutineResponseWithContext { apiService.getRepositories() }
    }


    override suspend fun getRepositoriesByName(owner : String, proj : String) : ApiResult<RepositoryDetailsDTO> {
       return coroutineResponseWithContext { apiService.getRepositoriesByName(owner, proj) }
    }
}