//File added for new activity for when user presses a movie
// Everything from the activity detail xml will be referenced here
package com.example.project1flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import okhttp3.Headers


private const val YOUTUBE_API_KEY = "AIzaSyCOx7XoTW6eK1kbUirpiYij3S8I-13RslQ"
private const val TRAILORS = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
class DetailActivity : YouTubeBaseActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvOverview: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var ytPlayerView: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        tvTitle = findViewById(R.id.tvTitle)
        tvOverview = findViewById(R.id.tvOverview)
        ratingBar = findViewById(R.id.rbVoteAverage)
        ytPlayerView = findViewById(R.id.player)

        //needed after everything is done with parcelable in MovieAdaptor
        val movie = intent.getParcelableExtra<Movie>(MOVIE_EXTRA) as Movie
        tvTitle.text = movie.title
        tvOverview.text = movie.overview
        ratingBar.rating = movie.voteAverage.toFloat()

        //for youtube player; it gets the urls of each video
        val client = AsyncHttpClient()
        client.get(TRAILORS.format(movie.movieId), object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.WARN
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                val results = json.jsonObject.getJSONArray("results")
                if (results.length() == 0) {
                    Log.WARN
                    return
                }
                val movieTrailorJson = results.getJSONObject(0)
                val youtubeKey = movieTrailorJson.getString("key")
                initializeYoutube(youtubeKey)
            }
        })

    }

    private fun initializeYoutube(youtubeKey: String) {
        ytPlayerView.initialize(YOUTUBE_API_KEY, object: YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean
            ) {
                player?.cueVideo(youtubeKey)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Log.WARN
            }

        })

    }
}