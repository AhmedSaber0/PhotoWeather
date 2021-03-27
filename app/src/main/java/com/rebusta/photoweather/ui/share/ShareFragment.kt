package com.rebusta.photoweather.ui.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.rebusta.photoweather.base.BaseFragment
import com.rebusta.photoweather.R
import com.rebusta.photoweather.databinding.FragmentShareBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShareFragment : BaseFragment<FragmentShareBinding, ShareViewModel>() {

    override val viewModel: ShareViewModel by viewModel()
    private val shareFragmentArgs by navArgs<ShareFragmentArgs>()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentShareBinding = FragmentShareBinding.inflate(inflater, container, attachToParent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        Glide.with(this).load(shareFragmentArgs.photoPath).placeholder(R.color.gray)
            .error(R.color.gray).into(binding.photoImv)
        binding.sharePhotoButton.setOnClickListener {
            sharePhoto(shareFragmentArgs.photoPath)
        }
    }
}