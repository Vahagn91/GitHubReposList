package com.example.sololearntesttask

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("repositories")
    suspend fun getRepositories(): Response<List<RepositoryListDTO?>>

    @GET("repos/{owner}/{proj}")
    suspend fun getRepositoriesByName(
        @Path(value = "owner") owner: String,
        @Path(value = "proj") proj: String
    ): Response<RepositoryDetailsDTO?>
}