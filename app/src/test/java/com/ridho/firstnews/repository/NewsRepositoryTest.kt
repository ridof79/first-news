import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ridho.firstnews.db.ArticleDao
import com.ridho.firstnews.db.ArticleDatabase
import com.ridho.firstnews.models.Article
import com.ridho.firstnews.models.Source
import com.ridho.firstnews.repository.NewsRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import java.io.IOException

class NewsRepositoryTest {

    private lateinit var newsRepository: NewsRepository
    private lateinit var articleDao: ArticleDao
    private lateinit var db: ArticleDatabase


    private val article1 = Article(1,
        "title1",
        "description1",
        "url1",
        "urlToImage1",
        Source("sourceId1", "sourceName1"),
        "publishedAt1",
        "publishedAt1",
        "publishedAt1",
    )

    private val article2 = Article(2,
        "title2",
        "description2",
        "url2",
        "urlToImage2",
        Source("sourceId2", "sourceName2"),
        "publishedAt2",
        "publishedAt2",
        "publishedAt2",
    )

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        articleDao = mock()
        db = mock {
            on { getArticleDao() } doReturn articleDao
        }
        newsRepository = NewsRepository(db)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun `test getSavedNews returns correct data`() = runBlocking {
        val article1 = Article(1,
            "title1",
            "description1",
            "url1",
            "urlToImage1",
            Source("sourceId1", "sourceName1"),
            "publishedAt1",
            "publishedAt1",
            "publishedAt1",
        )

        val article2 = Article(2,
            "title2",
            "description2",
            "url2",
            "urlToImage2",
            Source("sourceId2", "sourceName2"),
            "publishedAt2",
            "publishedAt2",
            "publishedAt2",
        )

        val articles = listOf(article1, article2)
        val expectedLiveData = MutableLiveData<List<Article>>()
        expectedLiveData.postValue(articles)
        val daoMock = mock<ArticleDao> {
            onBlocking { getAllArticles() } doReturn expectedLiveData
        }
        val dbMock = mock<ArticleDatabase> {
            on { getArticleDao() } doReturn daoMock
        }
        val repository = NewsRepository(dbMock)
        val observer = mock<Observer<List<Article>>>()
        repository.getSavedNews().observeForever(observer)
        verify(observer).onChanged(articles)
    }

    @Test
    fun testUpsertAddsArticleToDatabase() {
        runBlocking {
            // Call the function being tested
            newsRepository.upsert(article1)

            // Verify that the function being tested has been called
            verify(articleDao, times(1)).upsert(article1)
        }
    }

    @Test
    fun `test deleteArticle removes article from database`() = runBlocking {
        whenever(articleDao.deleteArticle(article1)).thenReturn(Unit)

        newsRepository.deleteArticle(article1)

        // Verify that articleDao's deleteArticle method was called with the correct argument
        verify(articleDao).deleteArticle(article1)
    }

}
