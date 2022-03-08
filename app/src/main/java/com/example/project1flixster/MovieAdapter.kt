package com.example.project1flixster

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val MOVIE_EXTRA = "MOVIE_EXTRA"
class MovieAdapter(private val context: Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

// inner class used to implement methods on the viewholder; we added View.onClickListener to add a button
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)

    // init is an initializer block used to do a task in a constructor (The constructor does not need to be declared)
    init {
        //Sets the button to the class ViewHolder (this)
        itemView.setOnClickListener(this)
    }

    //function (fun) that binds the items in our activities
        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            Glide.with(context).load(movie.posterImageUrl).into(ivPoster)

        }
//function (declared with fun) made for the button from the ViewHolder class
    override fun onClick(p0: View?) {
        //movies is the dataset with the list of movies and adapterPosition is a method that gets the position on the viewholder
        //Its gonna tell us which movie was tapped on
        val movies = movies[adapterPosition]
    //Makes the button work by calling the detail activity and now a user can access it
        val intent = Intent(context, DetailActivity::class.java)
    //need to import parcelable for this next step
        intent.putExtra(MOVIE_EXTRA, movies)
        context.startActivity(intent)
    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

}
