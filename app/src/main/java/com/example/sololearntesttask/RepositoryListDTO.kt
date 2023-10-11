package com.example.sololearntesttask

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RepositoryListDTO(

    val name: String,
    @SerializedName("full_name") val fullName: String,
    val description: String,
    val owner: OwnerDTO,
    var stargazers_count: Int
) : Serializable


data class OwnerDTO(
    val avatar_url: String,
    val login: String

) : Serializable