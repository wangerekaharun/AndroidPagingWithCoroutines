package ke.co.appslab.androidpagingwithcoroutines.networking

import ke.co.appslab.androidpagingwithcoroutines.models.RedditApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/r/aww/hot.json")
    fun fetchPosts(
        @Query("limit") loadSize: Int = 30,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ) : Deferred<Response<RedditApiResponse>>
}