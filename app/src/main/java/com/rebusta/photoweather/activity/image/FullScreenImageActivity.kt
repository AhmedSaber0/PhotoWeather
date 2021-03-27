package com.rebusta.photoweather.activity.image

import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.rebusta.photoweather.R
import com.rebusta.photoweather.base.BaseActivity
import com.rebusta.photoweather.databinding.ActivityFullScreenImageBinding

class FullScreenImageActivity :
    BaseActivity<ActivityFullScreenImageBinding, FullScreenImageViewModel>() {

    override val viewModel: FullScreenImageViewModel by viewModels()

    override fun initBinding(): ActivityFullScreenImageBinding =
        ActivityFullScreenImageBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {
        intent.extras?.let {
            Glide.with(this).load(it.getString("photo_path")).error(R.color.gray)
                .into(binding.image)
        }
        binding.closeImv.setOnClickListener {
            finish()
        }
    }
}
