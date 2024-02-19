package com.example.requestsample.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.requestsample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.state.collect {
                binding.lyrics.text = it.lyrics?.lyrics
                loading(it.loading)
                it.error?.let { throwable ->
                    Toast.makeText(
                        this@MainActivity, throwable.message ?: "", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupListeners() {
        with(binding) {
            btnGetLyrics.setOnClickListener {
                viewModel.onEvent(
                    MainVM.Event.GetLyricsEvent(
                        etArtist.text.toString(), etTitle.text.toString()
                    )
                )
            }
        }
    }

    private fun loading(loading: Boolean = true) {
        binding.progressBar.isVisible = loading
        binding.groupViews.isVisible = !loading
    }
}