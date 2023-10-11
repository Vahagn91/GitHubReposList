package com.example.sololearntesttask


interface GitHubRepository {
    suspend fun getRepositories(resultCallback: ApiResultCallback<List<RepositoryListDTO?>>)
    suspend fun getRepositoriesByName(owner : String, proj : String,resultCallback: ApiResultCallback<RepositoryDetailsDTO?>)
}

class GitHubRepositoryImpl(private val apiService: GitHubApi) : GitHubRepository {

    override suspend fun getRepositories(resultCallback: ApiResultCallback<List<RepositoryListDTO?>>) {
        coroutineResponseWithContext(resultCallback) { apiService.getRepositories() }
    }

    override suspend fun getRepositoriesByName(owner : String, proj : String, resultCallback: ApiResultCallback<RepositoryDetailsDTO?>) {
        coroutineResponseWithContext(resultCallback) { apiService.getRepositoriesByName(owner, proj) }
    }
}