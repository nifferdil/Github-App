package com.example.samplegithubapp.github.api

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val repositoryList: List<Repository>
)

data class Repository(
    @SerializedName("id") val repoId: Long,
    val name: String,
    val description: String,
    @SerializedName("stargazers_count") val stargazersCount: Int,
    val owner: Owner
)  {
    val starCountForDisplay: String get() = "Stars: $stargazersCount"
}

data class Owner(
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)

