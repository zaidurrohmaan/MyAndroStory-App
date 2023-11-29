package com.example.myandrostory.ui.story

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.example.myandrostory.R
import com.example.myandrostory.databinding.ActivityStoryBinding
import com.example.myandrostory.ui.ViewModelFactory
import com.example.myandrostory.utils.UserPreferences
import com.example.myandrostory.utils.dataStore
import kotlinx.coroutines.flow.first

class StoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@StoryActivity)
        val viewModel: StoryViewModel by viewModels {
            factory
        }
        val adapter = StoryAdapter()
        binding.rvStory.adapter = adapter
        viewModel.story.observe(this) {
            Log.d("Observe Paging Data", "Observe Paging Data")
            adapter.submitData(lifecycle, it)
        }

//        val pref = UserPreferences.getInstance(application.dataStore)
//        pref.getUserToken().asLiveData().observe(this){
//            binding.tvToken.text = it
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_logout -> {

            }

            R.id.btn_maps -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
}
