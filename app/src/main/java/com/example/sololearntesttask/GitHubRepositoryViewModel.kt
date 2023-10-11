package com.example.sololearntesttask

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

open class GitHubRepositoryViewModel(private val interactor: GitReposInteractor) : ViewModel() {

    private val _reposList = MutableLiveData<List<RepositoryListDTO?>?>()
    val reposList: LiveData<List<RepositoryListDTO?>?>
        get() = _reposList

    private val _reposDetails = MutableLiveData<RepositoryDetailsDTO?>()
    val reposDetails: LiveData<RepositoryDetailsDTO?>
        get() = _reposDetails



}
