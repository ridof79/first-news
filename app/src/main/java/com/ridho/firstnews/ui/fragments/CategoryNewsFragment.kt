package com.ridho.firstnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ridho.firstnews.R
import com.ridho.firstnews.databinding.FragmentCategoryNewsBinding
import com.ridho.firstnews.ui.SourceNewsFragment

class CategoryNewsFragment : Fragment() {
    private lateinit var binding: FragmentCategoryNewsBinding
    private lateinit var newsCategory: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryNewsBinding.inflate(inflater, container, false)

        binding.btnGeneral.setOnClickListener {
            newsCategory = "general"
            navigateToSourceNewsFragment()
        }

        binding.btnBusiness.setOnClickListener {
            newsCategory = "business"
            navigateToSourceNewsFragment()
        }

        binding.btnEntertainment.setOnClickListener {
            newsCategory = "entertainment"
            navigateToSourceNewsFragment()
        }

        binding.btnHealth.setOnClickListener {
            newsCategory = "health"
            navigateToSourceNewsFragment()
        }

        binding.btnSports.setOnClickListener {
            newsCategory = "sports"
            navigateToSourceNewsFragment()
        }

        binding.btnTechnology.setOnClickListener {
            newsCategory = "technology"
            navigateToSourceNewsFragment()
        }

        binding.btnAllCategory.setOnClickListener {
            newsCategory = ""
            navigateToSourceNewsFragment()
        }

        return binding.root
    }

    private fun navigateToSourceNewsFragment() {
        val sourceNewsFragment = SourceNewsFragment()
        val args = Bundle()
        args.putString("newsCategory", newsCategory)
        sourceNewsFragment.arguments = args

        findNavController().navigate(
            R.id.action_categoryNewsFragment_to_sourceNewsFragment,
            args
        )
    }
}
