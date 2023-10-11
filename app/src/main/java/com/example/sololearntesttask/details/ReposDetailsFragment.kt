package com.example.sololearntesttask.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.sololearntesttask.R
import com.example.sololearntesttask.databinding.FragmentReposDetailsBinding
import com.example.sololearntesttask.main.GitHubRepositoryViewModel
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

        val avatarUrl = args.details?.owner?.avatarUrl


        Glide.with(binding?.root?.context!!)
            .load(avatarUrl)
            .placeholder(R.drawable.ic_person)
            .into(binding!!.avatarImageView)

        binding?.textName?.text = args.details?.name
        binding?.textDescription?.text = args.details?.description
        binding?.starCount?.text = args.details?.stargazersCount.toString()
        binding?.forksCount?.text = args.details?.forks.toString()
        binding?.language?.text = args.details?.language
        binding?.url?.text = args.details?.url

    }


}