package com.jandromuso.mymovies.ui.main

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.jandromuso.mymovies.ui.detail.DetailActivity
import com.jandromuso.mymovies.R
import com.jandromuso.mymovies.client.MovieDbClient
import com.jandromuso.mymovies.databinding.ActivityMainBinding
import com.jandromuso.mymovies.model.Movie
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
            val message = when {
                isGranted -> "Permission Granted"
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> "Should show Rationale"
                else -> "Permission Rejected"
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moviesAdapter = MoviesAdapter(emptyList()) { navigateTo(it) }
        binding.recycler.adapter = moviesAdapter
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)

        lifecycleScope.launch{
            val apiKey = getString(R.string.api_key)
            val popularMovies = MovieDbClient.service.listPopularMovies(apiKey)
            moviesAdapter.movies = popularMovies.results
            //Esto indica al adapter que tiene que rerenderizar la vista
            moviesAdapter.notifyDataSetChanged()
        }

    }

    private fun navigateTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie)
        startActivity(intent)
    }
}