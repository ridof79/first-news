package com.ridho.firstnews.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.ridho.firstnews.api.RetrofitInstance
import com.ridho.firstnews.db.ArticleDatabase
import com.ridho.firstnews.models.Article

class NewsRepository(
    private val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun getNewsByCategoryAndSource(newsCategory: String, sourceCountry: String, pageNumber: Int) =
        RetrofitInstance.api.getNewsByCategoryAndCountry(newsCategory, sourceCountry, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)

    fun isArticleSaved(articleUrl: String): LiveData<Boolean> {
        return db.getArticleDao().getArticleByUrl(articleUrl).map {
            it != null
        }
    }
}