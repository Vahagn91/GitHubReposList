package com.example.sololearntesttask.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sololearntesttask.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    private val gitRepoViewModel: GitHubRepositoryViewModel by viewModel()

    private lateinit var adapter: RepositoryAdapter


    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gitRepoViewModel.getSortedRepoWithDetails()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        observeLiveData()


    }

    fun observeLiveData() {

        gitRepoViewModel.reposListLiveData.observe(viewLifecycleOwner) {
            adapter.updateList(it)
            binding?.loader?.hide()
        }

        gitRepoViewModel.errorLiveData.observe(viewLifecycleOwner) {
            binding?.loader?.hide()
            val snackbar = binding?.root?.let { it1 ->
                Snackbar
                    .make(it1, it, Snackbar.LENGTH_LONG)
            }
            snackbar?.show()
        }


    }


    fun initAdapter() {
        adapter = RepositoryAdapter { details ->
            findNavController().navigate(
                MainFragmentDirections.actionToReposDetailsFragment(details)
            )
        }
        binding?.recyclerView?.layoutManager = LinearLayoutManager(this.context)
        binding?.recyclerView?.adapter = adapter
    }

}
