package ke.co.appslab.androidpagingwithcoroutines.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import ke.co.appslab.androidpagingwithcoroutines.models.RedditPost
import ke.co.appslab.androidpagingwithcoroutines.networking.ApiClient
import ke.co.appslab.androidpagingwithcoroutines.networking.ApiService
import ke.co.appslab.androidpagingwithcoroutines.utils.safeApiCall
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostsDataSource : PageKeyedDataSource<String,RedditPost>() {
    private val apiService = ApiClient.getClient().create(ApiService::class.java)

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, RedditPost>) {
        GlobalScope.launch {
            try {
                val response = apiService.fetchPosts(loadSize = params.requestedLoadSize).await()
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
        GlobalScope.launch {
            try {
                val response = apiService.fetchPosts(loadSize = params.requestedLoadSize,after = params.key).await()
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
        GlobalScope.launch {
            try {
                val response = apiService.fetchPosts(loadSize = params.requestedLoadSize,  before = params.key).await()
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

}