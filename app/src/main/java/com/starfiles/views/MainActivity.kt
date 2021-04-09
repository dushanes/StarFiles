package com.starfiles.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.starfiles.R
import com.starfiles.adapters.CharacterPagingAdapter
import com.starfiles.databinding.ActivityMainBinding
import com.starfiles.viewmodel.CharactersListViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var charactersListViewModel: CharactersListViewModel
    private val linearLayoutManager = LinearLayoutManager(this)
    private val compositeDisposable = CompositeDisposable()
    private val adapter = CharacterPagingAdapter(supportFragmentManager)
    private var searchJob: Job? =null

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)
        setContentView(binding.root)

        charactersListViewModel = ViewModelProvider(this).get(CharactersListViewModel::class.java)

        binding.lifecycleOwner = this
        binding.characterListViewModel = charactersListViewModel
        binding.mainScrollView.layoutManager = linearLayoutManager
        lifecycleScope.launch {
            charactersListViewModel.getCharacters("").collectLatest {
                adapter.submitData(lifecycle, it)
            }
        }
        binding.searchButton.setOnClickListener { search(binding.searchInput.text.toString()) }
        binding.mainScrollView.adapter = adapter
    }

    private fun search(query:String){
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            charactersListViewModel.getCharacters(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}