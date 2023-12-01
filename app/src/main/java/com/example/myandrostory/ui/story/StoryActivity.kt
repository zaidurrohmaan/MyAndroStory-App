package com.example.myandrostory.ui.story

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myandrostory.R
import com.example.myandrostory.data.response.StoryItem
import com.example.myandrostory.databinding.ActivityStoryBinding
import com.example.myandrostory.ui.ViewModelFactory
import com.example.myandrostory.ui.add_story.AddStoryActivity
import com.example.myandrostory.ui.detail.DetailStoryActivity
import com.example.myandrostory.ui.login.LoginActivity
import com.example.myandrostory.ui.map.MapsActivity

class StoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvStory.layoutManager = LinearLayoutManager(this)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@StoryActivity)
        val viewModel: StoryViewModel by viewModels {
            factory
        }
        val adapter = StoryAdapter()

        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{ adapter.retry() }
        )

        viewModel.token.observe(this) {
            binding.token.text = it
            viewModel.story.observe(this) {
                adapter.submitData(lifecycle, it)
            }
        }

        adapter.setOnItemClickCallback(object: StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: StoryItem) {
                Intent(this@StoryActivity, DetailStoryActivity::class.java).also {
                    it.putExtra(DetailStoryActivity.EXTRA_NAME, data.name)
                    it.putExtra(DetailStoryActivity.EXTRA_PHOTO_URL, data.photoUrl)
                    it.putExtra(DetailStoryActivity.EXTRA_DESC, data.description)
                    startActivity(it)
                }
            }
        })

        binding.fab.setOnClickListener {
            startActivity(Intent(this@StoryActivity, AddStoryActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_logout -> {
                val factory: ViewModelFactory = ViewModelFactory.getInstance(this@StoryActivity)
                val viewModel: StoryViewModel by viewModels {
                    factory
                }
                viewModel.saveUserToken("")
                viewModel.saveUserSession(false)
                startActivity(Intent(this@StoryActivity, LoginActivity::class.java))
                finish()
            }
            R.id.btn_maps -> {
                startActivity(Intent(this@StoryActivity, MapsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
