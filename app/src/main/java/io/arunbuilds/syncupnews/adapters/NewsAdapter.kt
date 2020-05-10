package io.arunbuilds.syncupnews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.arunbuilds.syncupnews.R
import io.arunbuilds.syncupnews.api.model.Article
import kotlinx.android.synthetic.main.item_article_preview.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_article_preview, parent, false)
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }


    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(article: Article) {
            itemView.apply {
                tvNewsTitle.text = article.title
                tvDescription.text = article.description
                tvDate.text = article.publishedAt
                tvNewsSource.text = article.source.name
                Glide.with(this).load(article.urlToImage).into(ivArticlePic)
                setOnClickListener {
                    onItemClickListener?.let {
                        it(article)
                    }
                }
            }
        }
    }

    private val diffUtilCallBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffUtilCallBack)

    private var onItemClickListener: ((Article) -> Unit)? = null
    fun setOnclickListener(listener: ((Article) -> Unit)) {
        onItemClickListener = listener
    }

    fun submitList(listofArticles: List<Article>) {
        differ.submitList(listofArticles)
    }

}