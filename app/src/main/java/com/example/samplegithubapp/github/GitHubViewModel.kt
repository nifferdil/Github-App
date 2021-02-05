package com.example.samplegithubapp.github

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.samplegithubapp.github.api.GitHubService
import com.example.samplegithubapp.github.api.Resource
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class GitHubViewModel(private val apiService: GitHubService) : ViewModel() {

    companion object {
        const val DAYS = 30
        const val PARAM_SORT= "stars"
        const val PARAM_ORDER = "desc"
        const val PARAM_PER_PAGE = 100
        const val DEFAULT_ERROR_MESSAGE = "Something went wrong, please try again later"
    }

    fun getRepos() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiService.getRepos(
                repoCreatedDaysAgo(DAYS),
                PARAM_SORT,
                PARAM_ORDER,
                PARAM_PER_PAGE
                )))
        } catch (exception: Exception){
            emit(Resource.error(data = null, message = exception.message?: DEFAULT_ERROR_MESSAGE))
        }
    }

    fun repoCreatedDaysAgo(daysAgo: Int): String {
        val date = Calendar.getInstance()
        date.add(Calendar.DATE, -daysAgo)
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate = sdf.format(date.time)
        return "created:$formattedDate"
    }
}
