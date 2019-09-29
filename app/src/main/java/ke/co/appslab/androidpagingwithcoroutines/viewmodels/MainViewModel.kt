package ke.co.appslab.androidpagingwithcoroutines.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ke.co.appslab.androidpagingwithcoroutines.models.RedditPost
import ke.co.appslab.androidpagingwithcoroutines.repositories.PostsDataSource

class MainViewModel : ViewModel() {
    var postsLiveData  :LiveData<PagedList<RedditPost>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()
        postsLiveData  = initializedPagedListBuilder(config).build()
    }

    fun getPosts():LiveData<PagedList<RedditPost>> = postsLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<String, RedditPost> {

        val dataSourceFactory = object : DataSource.Factory<String, RedditPost>() {
            override fun create(): DataSource<String, RedditPost> {
                return PostsDataSource(viewModelScope)
            }
        }
        return LivePagedListBuilder<String, RedditPost>(dataSourceFactory, config)
    }
}