package com.example.samplegithubapp.github.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.samplegithubapp.github.api.Response

/*
* Service API for GitHub
*/

interface GitHubService {

    @GET("users/{user}/repos")
    suspend fun getUsers(
        @Path("user") user: String
    ): List<Repository>

    @GET("search/repositories")
    suspend fun getRepos(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int
    ): Response
}