package com.example.sololearntesttask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sololearntesttask.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope


class MainFragment : Fragment() {

    private val gitRepoViewModel: GitHubRepositoryViewModel by viewModel()

    private lateinit var adapter: RepositoryAdapter


    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding

    private var count: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        gitRepoViewModel.getRepositories()

        observeLiveData()


    }

    fun observeLiveData() {

        gitRepoViewModel.reposList.observe(viewLifecycleOwner) {
            count = it!!.size
            adapter.updateList(it)
            getStarsForRepos(it)
        }

        gitRepoViewModel.reposDetails.observe(viewLifecycleOwner) {
            it?.let { it1 -> adapter.getItem(it1.position).stargazers_count = it.stargazers_count }
            it?.let { it1 -> adapter.notifyItemChanged(it1.position) }

//            count--
//
//            Log.d("count", "$count")
//            println("============================ $count")
//
//            if (count == 0) {
            val sortedList = adapter.getList()?.sortedByDescending { it?.stargazers_count }
            adapter.updateList(sortedList)
//            }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GitRepoApp.mCurrentFragment = this

    }


    fun getStarsForRepos(repositoryListDTOS: List<RepositoryListDTO?>?) {
//        coroutineScope {
//
//            val deferredResults = repositoryListDTOS?.mapIndexed { i, item ->
//                item?.owner?.let {
//                    // Use async to make API calls concurrently
//                    async {
//                        gitRepoViewModel.getRepositoriesByName(
//                            owner = it.login,
//                            proj = item.name,
//                            i
//                        )
//                    }
//                }
//            }
//
//// Wait for all API calls to complete
//            runBlocking {
//                deferredResults?.awaitAll()
//            }
//        }


        repositoryListDTOS?.forEachIndexed { i, item ->

            item?.owner?.let {
                gitRepoViewModel.getRepositoriesByName(
                    owner = it.login,
                    proj = item.name, i
                )

            }
        }
//        repositoryListDTOS?.get(4)?.owner?.let {
//            gitRepoViewModel.getRepositoriesByName(
//                owner = it.login,
//                proj = repositoryListDTOS.get(4)!!.name, 4
//            )
//
//        }


    }

    fun initAdapter() {
        adapter = RepositoryAdapter { login, name, position ->
            findNavController().navigate(
                MainFragmentDirections.actionToReposDetailsFragment(
                    login,
                    name,
                    position
                )
            )
        }
        binding?.recyclerView?.layoutManager = LinearLayoutManager(this.context)
        binding?.recyclerView?.adapter = adapter
    }

}
