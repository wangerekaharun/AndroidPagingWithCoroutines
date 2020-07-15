package ke.co.appslab.androidpagingwithcoroutines.repositories

import android.util.Log
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagingSource
import ke.co.appslab.androidpagingwithcoroutines.models.RedditPost
import ke.co.appslab.androidpagingwithcoroutines.networking.ApiClient
import ke.co.appslab.androidpagingwithcoroutines.networking.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class PostsPagingSource :
    PagingSource<String, RedditPost>() {
    private val apiService = ApiClient.getClient().create(ApiService::class.java)

    override suspend fun load(params: LoadParams<String>): LoadResult<String, RedditPost> {
        return try {
            val response = apiService.fetchPosts(loadSize = params.loadSize)
            val listing = response.body()?.data
            val redditPosts = listing?.children?.map { it.data }
            LoadResult.Page(
                redditPosts ?: listOf(),
                listing?.before,
                listing?.after
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override val keyReuseSupported: Boolean = true
}