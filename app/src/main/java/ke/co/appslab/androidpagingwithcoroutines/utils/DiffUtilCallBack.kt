package ke.co.appslab.androidpagingwithcoroutines.utils

import androidx.recyclerview.widget.DiffUtil
import ke.co.appslab.androidpagingwithcoroutines.models.RedditPost

class DiffUtilCallBack : DiffUtil.ItemCallback<RedditPost>() {
    override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
        return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
        return oldItem.title == newItem.title
                && oldItem.score == newItem.score
                && oldItem.commentCount == newItem.commentCount
    }

}