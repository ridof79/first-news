package com.ridho.firstnews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ridho.firstnews.databinding.ItemArticlePreviewBinding
import com.ridho.firstnews.models.Article
import com.ridho.firstnews.models.Source
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mockito.mock

class NewsAdapterTest {

    private lateinit var newsAdapter: NewsAdapter

    @Before
    fun setUp() {
        newsAdapter = NewsAdapter()
    }

    @Test
    fun `test if adapter is not null`() {
        assertNotNull(newsAdapter)
    }

    @Test
    fun `test if adapter returns correct item count`() {
        val articleList = listOf(
            Article(1,"title1", "desc1", "url1", "urlToImage1",  Source("name1","1"),"publishedAt1", "1","1"),
            Article(1,"title2", "desc2", "url2", "urlToImage2",  Source("name2","2"),"publishedAt2", "2","2")
        )
        newsAdapter.differ.submitList(articleList)
        assertEquals(2, newsAdapter.itemCount)
    }

    @Test
    fun `test if adapter returns correct view holder`() {
        val parent = mock(ViewGroup::class.java)
        val viewType = 0
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticlePreviewBinding.inflate(inflater, parent, false)
        val holder = newsAdapter.ArticleViewHolder(binding)
        assertTrue(holder is NewsAdapter.ArticleViewHolder)
    }
}

