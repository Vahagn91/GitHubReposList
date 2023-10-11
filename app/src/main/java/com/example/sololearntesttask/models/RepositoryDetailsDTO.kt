package com.example.sololearntesttask.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RepositoryDetailsDTO(

    @SerializedName("owner") val owner: DetailsOwnerDTO,
    @SerializedName("stargazers_count") val stargazersCount: Int,
    @SerializedName("forks") val forks: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("language") val language: String,
    @SerializedName("html_url") val url: String

    ) : Serializable

data class DetailsOwnerDTO(
    @SerializedName("avatar_url") val avatarUrl: String

) : Serializable