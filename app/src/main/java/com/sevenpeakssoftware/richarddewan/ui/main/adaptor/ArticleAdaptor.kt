package com.sevenpeakssoftware.richarddewan.ui.main.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.sevenpeakssoftware.richarddewan.R
import com.sevenpeakssoftware.richarddewan.data.local.entity.ArticleEntity
import com.sevenpeakssoftware.richarddewan.utils.DateTimeUtil
import kotlin.collections.ArrayList


class ArticleAdaptor(
    private var mList: List<ArticleEntity>,
    private var dateTimeUtil: DateTimeUtil,
    private var viewPreloadSizeProvider: ViewPreloadSizeProvider<String>
) : RecyclerView.Adapter<ArticleAdaptor.ArticleViewHolder>(),
    ListPreloader.PreloadModelProvider<String> {

    private lateinit var mView: View

    fun setArticle(articleList: List<ArticleEntity>) {
        mList = articleList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        mView = LayoutInflater.from(parent.context).inflate(
            R.layout.car_list_view, parent, false
        )
        return ArticleViewHolder(mView, dateTimeUtil,viewPreloadSizeProvider)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.onBind(mList[position])
    }

    class ArticleViewHolder(
        itemView: View,
        private val dateTimeUtil: DateTimeUtil,
        private val viewPreloadSizeProvider: ViewPreloadSizeProvider<String>
    ) : RecyclerView.ViewHolder(itemView) {

        private var mTitle: TextView = itemView.findViewById(R.id.txtTitle)
        private var mDate: TextView = itemView.findViewById(R.id.txtDate)
        private var mIngress: TextView = itemView.findViewById(R.id.txtIngress)
        private var mCarImageView: ImageView = itemView.findViewById(R.id.carImageView)

        fun onBind(article: ArticleEntity) {

            /*
            configure Glide cache option
             */
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

            Glide.with(itemView)
                .load(article.image)
                .placeholder(R.drawable.ic_placeholder)
                .apply(requestOptions)
                .into(mCarImageView)

            mTitle.text = article.title
            mDate.text = dateTimeUtil.getDateTime(article.created)
            mIngress.text = article.ingress

            viewPreloadSizeProvider.setView(mCarImageView)

        }
    }

    override fun getPreloadItems(position: Int): MutableList<String> {
        val url = mList[position].image
        if (url.isEmpty()) {
            return mutableListOf()
        }
        return mutableListOf(url)
    }

    override fun getPreloadRequestBuilder(item: String): RequestBuilder<*>? {
        return Glide.with(mView)
            .load(item)
    }
}