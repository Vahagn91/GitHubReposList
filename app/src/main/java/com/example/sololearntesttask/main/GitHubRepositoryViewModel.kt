package com.example.sololearntesttask.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.sololearntesttask.GitRepoApp
import com.example.sololearntesttask.network.GitReposInteractor
import com.example.sololearntesttask.db.AppDatabase
import com.example.sololearntesttask.db.CachedDataEntity
import com.example.sololearntesttask.models.GitReposFullDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

open class GitHubRepositoryViewModel(private val interactor: GitReposInteractor) : ViewModel() {

    private val _reposListLiveData = MutableLiveData<List<GitReposFullDTO>>()
    val reposListLiveData: LiveData<List<GitReposFullDTO>>
        get() = _reposListLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData


    fun getSortedRepoWithDetails() {

        val database =
            Room.databaseBuilder(GitRepoApp.instance, AppDatabase::class.java, "app_database")
                .build()

        val dataId = "github_repos"

        viewModelScope.launch(Dispatchers.IO) {
            val cachedDataEntity = database.cachedDataDao().getCachedData(dataId)
            if (cachedDataEntity != null) {
                val cachedData = cachedDataEntity.data

                val gson = Gson()
                val reposListType = object : TypeToken<List<GitReposFullDTO>>() {}.type
                val reposList = gson.fromJson<List<GitReposFullDTO>>(cachedData, reposListType)

                _reposListLiveData.postValue(reposList)
            } else {
                try {
                    val data = interactor.getRepositoriesWithDetails()
                    val sortedList =
                        data.sortedByDescending { it.gitRepoDetailDTO.stargazersCount }

                    val gson = Gson()
                    val json = gson.toJson(sortedList)

                    val newCachedDataEntity = CachedDataEntity(id = dataId, data = json)
                    database.cachedDataDao().insertOrUpdateCachedData(newCachedDataEntity)

                    _reposListLiveData.postValue(sortedList)
                } catch (e: Exception) {
                    _errorLiveData.postValue(e.message)
                }
            }
        }
    }

}
