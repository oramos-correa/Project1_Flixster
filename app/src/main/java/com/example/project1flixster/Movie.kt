package com.example.project1flixster

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray

@Parcelize
data class Movie (
    val movieId: Int,
    private val posterPath: String,
    val title: String,
    val overview: String,
    val voteAverage: Double,
) : Parcelable {
    @IgnoredOnParcel
    val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"
    companion object {
        fun fromJsonArray(movieJsonArr: JSONArray): List<Movie> {
            val m = mutableListOf<Movie>()
            for (i in 0 until movieJsonArr.length()){
                val movieJson = movieJsonArr.getJSONObject(i)
                m.add(
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview"),
                        movieJson.getDouble("vote_average")

                    )
                )
            }

            return m

        }
    }
}