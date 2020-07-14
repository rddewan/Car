package com.sevenpeakssoftware.richarddewan.ui.main.adaptor


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.sevenpeakssoftware.richarddewan.R
import com.sevenpeakssoftware.richarddewan.data.local.entity.ArticleEntity
import com.sevenpeakssoftware.richarddewan.data.remote.response.Content
import com.sevenpeakssoftware.richarddewan.di.scope.ActivityScope
import com.sevenpeakssoftware.richarddewan.utils.DateTimeUtil
import kotlinx.android.synthetic.main.car_list_view.view.*


@ActivityScope
class ArticleAdaptor(
    private var mList: ArrayList<ArticleEntity>,
    private var dateTimeUtil: DateTimeUtil
) : RecyclerView.Adapter<ArticleAdaptor.ArticleViewHolder>() {

    fun setArticle(articleList: ArrayList<ArticleEntity>){
        mList = articleList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.car_list_view,parent,false
        )
        return ArticleViewHolder(view,dateTimeUtil)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.onBind(mList[position])
    }

    class ArticleViewHolder(itemView: View,private val dateTimeUtil: DateTimeUtil) : RecyclerView.ViewHolder(itemView) {

        fun onBind(article: ArticleEntity) {

            Glide.with(itemView)
                .load(article.image)
                .apply(RequestOptions().signature(ObjectKey(article.changed)))
                .placeholder(R.drawable.ic_placeholder)
                .into(itemView.carImageView)

            itemView.txtTitle.text = article.title
            itemView.txtDate.text = dateTimeUtil.getDateTime(article.created)
            itemView.txtIngress.text = article.ingress

        }
    }
}