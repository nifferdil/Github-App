package com.example.samplegithubapp.github

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.samplegithubapp.R
import com.example.samplegithubapp.github.api.Repository

class RepoListAdapter(private val repoList: ArrayList<Repository>) : RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.repo_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(repoList.get(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(repo: Repository) {

            val name = itemView.findViewById<TextView>(R.id.name)
            val title = itemView.findViewById<TextView>(R.id.title)
            val starCount = itemView.findViewById<TextView>(R.id.star_count)

            name.text = repo.name
            title.text = repo.owner.login
            starCount.text = repo.starCountForDisplay
        }
    }
}