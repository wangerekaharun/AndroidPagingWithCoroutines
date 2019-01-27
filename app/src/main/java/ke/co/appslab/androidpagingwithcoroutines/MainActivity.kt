package ke.co.appslab.androidpagingwithcoroutines

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import ke.co.appslab.androidpagingwithcoroutines.adapters.RedditPostsAdapter
import ke.co.appslab.androidpagingwithcoroutines.models.RedditPost
import ke.co.appslab.androidpagingwithcoroutines.repositories.PostsDataSource

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private val redditPostsAdapter = RedditPostsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

       initializeList()
    }

    private fun initializeList() {
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = redditPostsAdapter

        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()

        val liveData = initializedPagedListBuilder(config).build()

        liveData.observe(this, Observer<PagedList<RedditPost>> { pagedList ->
            redditPostsAdapter.submitList(pagedList)
        })
    }
    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<String, RedditPost> {

        val dataSourceFactory = object : DataSource.Factory<String, RedditPost>() {
            override fun create(): DataSource<String, RedditPost> {
                return PostsDataSource()
            }
        }
        return LivePagedListBuilder<String, RedditPost>(dataSourceFactory, config)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
