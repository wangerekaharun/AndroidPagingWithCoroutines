package ke.co.appslab.androidpagingwithcoroutines.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import ke.co.appslab.androidpagingwithcoroutines.R
import ke.co.appslab.androidpagingwithcoroutines.models.RedditPost
import ke.co.appslab.androidpagingwithcoroutines.utils.DiffUtilCallBack
import kotlinx.android.synthetic.main.adapter_row.view.*

class RedditPostsAdapter :
    PagingDataAdapter<RedditPost, RedditPostsAdapter.MyViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bindPost(it) }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val scoreText: TextView = itemView.score
        private val commentsText: TextView = itemView.comments
        private val titleText: TextView = itemView.title

        fun bindPost(redditPost: RedditPost) {
            with(redditPost) {
                scoreText.text = score.toString()
                commentsText.text = commentCount.toString()
                titleText.text = title
            }
        }
    }
}