package com.ridho.firstnews.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ridho.firstnews.models.Article
import com.ridho.firstnews.models.Source
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class ArticleDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var articleDao: ArticleDao

    private val testArticle = Article(1,
        "title1",
        "description1",
        "url1",
        "urlToImage1",
        Source("sourceId1", "sourceName1"),
        "publishedAt1",
        "publishedAt1",
        "publishedAt1",
    )

    @Before
    fun setup() {
        whenever(articleDao.getAllArticles()).thenReturn(MutableLiveData(listOf(testArticle)))
        whenever(articleDao.getArticleByUrl(testArticle.url)).thenReturn(MutableLiveData(testArticle))
    }

    @Test
    fun testGetAllArticles() = runTest {
        // given
        val expectedArticles = listOf(testArticle)

        // when
        val articlesLiveData = articleDao.getAllArticles().getOrAwaitValue()

        // then
        assertEquals(expectedArticles, articlesLiveData)
    }


    @Test
    fun testGetArticleByUrl() = runTest {
        // when
        val article = articleDao.getArticleByUrl(testArticle.url).getOrAwaitValue()

        // then
        assertEquals(testArticle, article)
    }

    @Test
    fun testUpsert() = runTest {
        // when
        articleDao.upsert(testArticle)

        // then
        verify(articleDao).upsert(testArticle)
    }

    @Test
    fun testDeleteArticle() = runTest {
        // when
        articleDao.deleteArticle(testArticle)

        // then
        verify(articleDao).deleteArticle(testArticle)
    }

    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(value: T) {
                data = value
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)
        afterObserve.invoke()
        if (!latch.await(time, timeUnit)) {
            this.removeObserver(observer)
            throw TimeoutException("LiveData value was never set.")
        }
        @Suppress("UNCHECKED_CAST")
        return data as T
    }

}
