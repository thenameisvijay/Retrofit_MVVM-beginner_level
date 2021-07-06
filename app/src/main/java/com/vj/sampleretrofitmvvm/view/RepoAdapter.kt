package com.vj.sampleretrofitmvvm.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vj.sampleretrofitmvvm.R
import com.vj.sampleretrofitmvvm.databinding.LayoutItemBinding
import com.vj.sampleretrofitmvvm.model.RepoResponse

class RepoAdapter : RecyclerView.Adapter<RepoAdapter.DataViewHolder>() {

    private val gitUsers: ArrayList<RepoResponse> = arrayListOf()

    inner class DataViewHolder(itemView: LayoutItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        fun bind(user: RepoResponse) {
            val profilePic: AppCompatImageView = itemView.findViewById(R.id.profile_pic)
            val userLoginName: AppCompatTextView = itemView.findViewById(R.id.user_login)
            Picasso.get().load(user.avatar_url).resize(90, 90).centerCrop().into(profilePic)
            userLoginName.text = user.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = gitUsers.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(gitUsers[position])

    fun addGitUsers(gitUsers: ArrayList<RepoResponse>) {
        this.gitUsers.apply {
            clear()
            addAll(gitUsers)
        }
    }
}