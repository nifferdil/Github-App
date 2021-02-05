package com.example.samplegithubapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplegithubapp.databinding.ActivityMainBinding
import com.example.samplegithubapp.github.GitHubViewModel
import com.example.samplegithubapp.github.RepoListAdapter
import com.example.samplegithubapp.github.ViewModelFactory
import com.example.samplegithubapp.github.ApiClient
import com.example.samplegithubapp.github.api.*

class MainActivity : AppCompatActivity()  {

    lateinit var mainBinding: ActivityMainBinding

    var repoList = ArrayList<Repository>()

    private lateinit var viewModel: GitHubViewModel
    private lateinit var adapter: RepoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainBinding.mainActivity = this

        setupViewModel()

        setupUI()

        setUpObserver()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiClient.apiClient().create(GitHubService::class.java))
        ).get(GitHubViewModel::class.java)
    }

    private fun setupUI() {
        mainBinding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        mainBinding.recyclerView.setItemAnimator(DefaultItemAnimator())

        adapter = RepoListAdapter(repoList)
        mainBinding.recyclerView.adapter = adapter
        mainBinding.recyclerView.addItemDecoration(DividerItemDecoration(this@MainActivity,
            LinearLayoutManager.VERTICAL))

    }

    private fun setUpObserver() {
        viewModel.getRepos().observe(this, Observer {

            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        showProgress(false)
                        resource.data?.let { repos -> retrieveList(repos) }
                    }
                    Status.ERROR -> {
                        showProgress(false)
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        Log.d("stattus_code","" + it.data)
                    }
                    Status.LOADING -> {
                        showProgress(true)
                    }
                }
            }
        })
    }

    private fun retrieveList(response: Response) {
        repoList.addAll(response.repositoryList)
        adapter.notifyDataSetChanged()
    }

    private fun showProgress(status: Boolean) {
        if (status) {
            mainBinding.showProgress.visibility = View.VISIBLE
        } else {
            mainBinding.showProgress.visibility = View.GONE
        }
    }
}
