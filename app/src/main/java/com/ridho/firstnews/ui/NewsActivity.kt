package com.ridho.firstnews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ridho.firstnews.R
import com.ridho.firstnews.databinding.ActivityNewsBinding
import com.ridho.firstnews.db.ArticleDatabase
import com.ridho.firstnews.repository.NewsRepository

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        fun findNavController(activity: FragmentActivity): NavController? {
            val navHostFragment = activity.supportFragmentManager
                .findFragmentById(R.id.newsNavHostFragment) as? NavHostFragment
            return navHostFragment?.navController
        }


        findNavController(this)?.let { binding.bottomNavigationView.setupWithNavController(it) }

    }


}