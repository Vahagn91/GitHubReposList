package com.example.sololearntesttask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.sololearntesttask.databinding.FragmentReposDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReposDetailsFragment : Fragment() {

    private val gitRepoViewModel: GitHubRepositoryViewModel by viewModel()
    private val args by navArgs<ReposDetailsFragmentArgs>()


    private var _binding: FragmentReposDetailsBinding? = null

    private val binding get() = _binding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReposDetailsBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gitRepoViewModel.getRepositoriesByName(
            owner = args.login.toString(),
            proj = args.name.toString(),
            position = args.position
            )

        observeLiveData()


    }


    fun observeLiveData() {



        gitRepoViewModel.reposDetails.observe(viewLifecycleOwner) {

                binding?.textName?.text = it?.name
            binding?.textDescription?.text = it?.description
        }

    }


}