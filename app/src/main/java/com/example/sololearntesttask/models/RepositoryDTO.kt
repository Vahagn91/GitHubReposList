package com.example.sololearntesttask.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RepositoryDTO(

    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("description") val description: String,
    @SerializedName("owner") val owner: OwnerDTO,
) : Serializable


data class OwnerDTO(
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("login") val login: String

) : Serializable