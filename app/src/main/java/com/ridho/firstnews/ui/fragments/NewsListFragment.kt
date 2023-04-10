import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ridho.firstnews.adapters.NewsAdapterV2
import com.ridho.firstnews.api.RetrofitInstance
import com.ridho.firstnews.databinding.FragmentNewsListBinding
import com.ridho.firstnews.models.ArticleV2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsListFragment : Fragment() {
    private lateinit var binding: FragmentNewsListBinding
    private lateinit var newsCategory: String
    private lateinit var sourceCountry: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsListBinding.inflate(inflater, container, false)

        newsCategory = arguments?.getString("newsCategory") ?: ""
        sourceCountry = arguments?.getString("sourceCountry") ?: ""

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        val adapter = NewsAdapterV2()

        binding.newsList.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            this.adapter = adapter
        }

        GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.api.getNewsByCategoryAndCountry(newsCategory, sourceCountry)
            if (response.isSuccessful) {
                val articles = response.body()?.sources ?: emptyList<ArticleV2>()
                withContext(Dispatchers.Main) {
                    adapter.differ.submitList(articles)
                }
            }
        }
    }

    companion object {
        private const val ARG_SOURCE = "arg_source"

        fun newInstance(source: String): NewsListFragment {
            val args = Bundle()
            args.putString(ARG_SOURCE, source)
            val fragment = NewsListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
