package com.example.samplegithubapp.github

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.samplegithubapp.github.api.GitHubService

class ViewModelFactory(private val apiService: GitHubService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(GitHubViewModel::class.java)) {
            return GitHubViewModel(apiService) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}