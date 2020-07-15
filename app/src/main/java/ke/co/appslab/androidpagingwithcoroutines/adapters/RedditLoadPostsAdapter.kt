package ke.co.appslab.androidpagingwithcoroutines.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ke.co.appslab.androidpagingwithcoroutines.R
import kotlinx.android.synthetic.main.item_load_state_footer.view.*

class RedditLoadPostsAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<RedditLoadPostsAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(itemView: View, retry: () -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val tvErrorMessage: TextView = itemView.tvErrorMessage
        private val progressBar: ProgressBar = itemView.progress_bar
        private val btnRetry: Button = itemView.btnRetry

        init {
            btnRetry.setOnClickListener {
                retry.invoke()
            }
        }


        fun bindState(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                tvErrorMessage.error = loadState.error.localizedMessage
            }
            btnRetry.isVisible = loadState is LoadState.Error
            progressBar.isVisible = loadState is LoadState.Loading
            tvErrorMessage.isVisible = loadState is LoadState.Error

        }

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bindState(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_load_state_footer, parent, false)
        return LoadStateViewHolder(view, retry)
    }
}


