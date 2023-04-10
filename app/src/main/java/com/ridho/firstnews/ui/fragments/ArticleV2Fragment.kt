package com.ridho.firstnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.ridho.firstnews.R
import com.ridho.firstnews.adapters.NewsAdapter
import com.ridho.firstnews.adapters.NewsAdapterV2
import com.ridho.firstnews.databinding.FragmentArticleBinding
import com.ridho.firstnews.ui.NewsActivity
import com.ridho.firstnews.ui.NewsViewModel

class ArticleV2Fragment : Fragment(R.layout.fragment_article) {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapterV2
    private lateinit var binding: FragmentArticleBinding
    val args: ArticleFragmentArgs by navArgs()

    private val TAG = "BreakingNewsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        val articleV2 = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }

    }
}