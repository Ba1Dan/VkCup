package com.example.vkcup

import android.os.Bundle
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import androidx.lifecycle.ViewModelProvider
import com.example.vkcup.databinding.ActivityMainBinding
import com.google.android.flexbox.FlexboxLayoutManager


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isFirst: Boolean = true
    private var isUp: Boolean = true

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterTags: TagsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        initView()
        viewModel.stateScreen.observe(this) { stateScreen ->
            when(stateScreen) {
                is MainUiState.Result -> {
                    adapterTags.tags = stateScreen.tags
                    if (stateScreen.tags.count { it.isSelected } == 0) {
                        if (!isFirst) {
                            slideDown()
                        }
                        isFirst = false
                        isUp = true
                        binding.btnNext.visibility = View.INVISIBLE
                    } else {
                        if (isUp) {
                            slideUp()
                            isUp = false
                        }
                    }
                }
            }
        }

        binding.btnNext.setOnClickListener {
            Toast.makeText(this, getString(R.string.tags_continue), Toast.LENGTH_SHORT).show()
        }

        binding.btnLater.setOnClickListener {
            Toast.makeText(this, getString(R.string.tags_later), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        adapterTags = TagsAdapter()
        binding.rvTags.adapter = adapterTags
        binding.rvTags.layoutManager = FlexboxLayoutManager(this)

        adapterTags.clickTagListener = { tagItem ->
            viewModel.updateTag(tagItem)
        }
    }

    private fun slideUp() {
        binding.btnNext.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0f,
            0f,
            binding.btnNext.height.toFloat() + binding.btnNext.marginBottom.toFloat(),
            0f
        )
        animate.duration = 500
        animate.fillAfter = true
        binding.btnNext.startAnimation(animate)
    }

    private fun slideDown() {
        val animate = TranslateAnimation(
            0f,
            0f,
            0f,
            binding.btnNext.height.toFloat() + binding.btnNext.marginBottom.toFloat()
        )
        animate.duration = 500
        animate.fillAfter = true
        binding.btnNext.startAnimation(animate)
        binding.btnNext.visibility = View.GONE
    }
}