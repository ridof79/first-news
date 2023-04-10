package com.ridho.firstnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ridho.firstnews.R
import com.ridho.firstnews.databinding.FragmentSourceNewsBinding

class SourceNewsFragment : Fragment() {

    private var _binding: FragmentSourceNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsCategory: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSourceNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAr.setOnClickListener { navigateToNewsListFragment("ar") }
        binding.btnAu.setOnClickListener { navigateToNewsListFragment("au") }
        binding.btnFr.setOnClickListener { navigateToNewsListFragment("fr") }
        binding.btnDe.setOnClickListener { navigateToNewsListFragment("de") }
        binding.btnRu.setOnClickListener { navigateToNewsListFragment("ru") }
        binding.btnUs.setOnClickListener { navigateToNewsListFragment("us") }
        binding.btnJp.setOnClickListener { navigateToNewsListFragment("jp") }
        binding.btnAllSource.setOnClickListener { navigateToNewsListFragment("") }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToNewsListFragment(sourceCountry: String) {
        val newsListFragment = NewsListFragment()
        val args = Bundle()
        newsCategory = arguments?.getString("newsCategory") ?: ""
        args.putString("sourceCountry", sourceCountry)
        args.putString("newsCategory", newsCategory)
        newsListFragment.arguments = args

        findNavController().navigate(
            R.id.action_sourceNewsFragment_to_newsListFragment,
            args
        )
    }
}

