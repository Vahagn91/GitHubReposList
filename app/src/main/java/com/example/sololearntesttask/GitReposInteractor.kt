package com.example.sololearntesttask

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GitReposInteractor(private val repository: GitHubRepository) {

   suspend fun getRepositories() {


        repository.getRepositories(object : ApiResultCallback<List<RepositoryListDTO?>> {

                override fun onSuccess(response: List<RepositoryListDTO?>?) {
                    _reposList.postValue(response)
                }

                override fun onError(errorString: String?) {
//                    _reposList.postValue(null)
                    Log.e("error", errorString.toString())
                }
            })

    }

    suspend fun getRepositoriesByName(owner : String, proj : String, position:Int) {

        repository.getRepositoriesByName(owner,proj, object : ApiResultCallback<RepositoryDetailsDTO?> {

                override fun onSuccess(response: RepositoryDetailsDTO?) {
                    response?.position = position
                    _reposDetails.postValue(response)
                }

                override fun onError(errorString: String?) {
                    _reposDetails.postValue(null)
                }
            })
        }

}