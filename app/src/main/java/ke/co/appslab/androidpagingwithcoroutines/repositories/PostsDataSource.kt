package ke.co.appslab.androidpagingwithcoroutines.repositories

import android.util.Log
import androidx.paging.PageKeyedDataSource
import ke.co.appslab.androidpagingwithcoroutines.models.RedditPost
import ke.co.appslab.androidpagingwithcoroutines.networking.ApiClient
import ke.co.appslab.androidpagingwithcoroutines.networking.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PostsDataSource(coroutineContext: CoroutineContext) :
    PageKeyedDataSource<String, RedditPost>() {
    private val apiService = ApiClient.getClient().create(ApiService::class.java)

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, RedditPost>) {
        scope.launch {
            try {
                val response = apiService.fetchPosts(loadSize = params.requestedLoadSize)
                when{
                    response.isSuccessful -> {
                        val listing = response.body()?.data
                        val redditPosts = listing?.children?.map { it.data }
                        callback.onResult(redditPosts ?: listOf(), listing?.before, listing?.after)
                    }
                }

            }catch (exception : Exception){
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
            
        }

    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        scope.launch {
            try {
                val response =
                    apiService.fetchPosts(loadSize = params.requestedLoadSize, after = params.key)
                when{
                    response.isSuccessful -> {
                        val listing = response.body()?.data
                        val items = listing?.children?.map { it.data }
                        callback.onResult(items ?: listOf(), listing?.after)
                    }
                }

            }catch (exception : Exception){
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }

    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        scope.launch {
            try {
                val response =
                    apiService.fetchPosts(loadSize = params.requestedLoadSize, before = params.key)
                when{
                    response.isSuccessful -> {
                        val listing = response.body()?.data
                        val items = listing?.children?.map { it.data }
                        callback.onResult(items ?: listOf(), listing?.after)
                    }
                }

            }catch (exception : Exception){
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }

    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }

}