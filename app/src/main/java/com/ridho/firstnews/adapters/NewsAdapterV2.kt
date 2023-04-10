package com.ridho.firstnews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ridho.firstnews.R
import com.ridho.firstnews.databinding.ItemArticlev2PreviewBinding
import com.ridho.firstnews.models.ArticleV2

class NewsAdapterV2 : RecyclerView.Adapter<NewsAdapterV2.ArticleV2ViewHolder>() {
    inner class ArticleV2ViewHolder(val binding: ItemArticlev2PreviewBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ArticleV2>() {
        override fun areItemsTheSame(oldItem: ArticleV2, newItem: ArticleV2): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticleV2, newItem: ArticleV2): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleV2ViewHolder {
        val binding = ItemArticlev2PreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleV2ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleV2ViewHolder, position: Int) {
        val articleV2 = differ.currentList[position]
        holder.binding.apply {
            Glide.with(root.context)
                .load(articleV2.url)
                .apply(RequestOptions().placeholder(R.drawable.placeholder_view_vector))
                .into(ivArticleV2Image)
            tvSourceV2.text = articleV2.country
            tvTitleV2.text = articleV2.name
            tvDescriptionV2.text = articleV2.description
            tvPublishedAtV2.text = articleV2.name
            root.setOnClickListener{
                onItemClickListener?.let { it(articleV2) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((ArticleV2) -> Unit)? = null

    fun setOnItemClickListener(listener: (ArticleV2) -> Unit) {
        onItemClickListener = listener
    }
}
