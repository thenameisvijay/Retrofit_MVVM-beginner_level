package com.vj.sampleretrofitmvvm.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vj.sampleretrofitmvvm.databinding.ActivityMainBinding
import com.vj.sampleretrofitmvvm.model.GitRepository
import com.vj.sampleretrofitmvvm.model.RepoResponse
import com.vj.sampleretrofitmvvm.network.NetworkHelper
import com.vj.sampleretrofitmvvm.network.RepoClient
import com.vj.sampleretrofitmvvm.network.Status
import com.vj.sampleretrofitmvvm.viewmodel.RepoModelFactory
import com.vj.sampleretrofitmvvm.viewmodel.RepoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var viewModel: RepoViewModel
    private lateinit var mProgress: ProgressBar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        setupUI()
        setupViewModel()
        setupObservers()
    }

    private fun setupUI() {
        mRecyclerView = activityMainBinding.repoList
        mProgress = activityMainBinding.progress
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = RepoAdapter()
        mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                mRecyclerView.context,
                (mRecyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        mRecyclerView.adapter = mAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            RepoModelFactory(GitRepository(RepoClient.create()))
        ).get(RepoViewModel::class.java)
    }

    private fun setupObservers() {
        if (NetworkHelper.hasInternet(this)) {
            viewModel.getRepoList().observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mRecyclerView.visibility = View.VISIBLE
                            mProgress.visibility = View.GONE
                            resource.data?.let { gitUsers ->
                                setToAdapter(gitUsers)
                            }
                        }
                        Status.ERROR -> {
                            mRecyclerView.visibility = View.VISIBLE
                            mProgress.visibility = View.GONE
                            Log.e("ERROR", "error msg: " + it.message)
                        }
                        Status.LOADING -> {
                            mProgress.visibility = View.VISIBLE
                            mRecyclerView.visibility = View.GONE
                        }
                    }
                }
            }
        } else {
            mProgress.visibility = View.GONE
            mRecyclerView.visibility = View.GONE
            activityMainBinding.errorMesg.visibility = View.VISIBLE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setToAdapter(gitUsers: ArrayList<RepoResponse>) {
        mAdapter.apply {
            addGitUsers(gitUsers)
            notifyDataSetChanged()
        }
    }
}