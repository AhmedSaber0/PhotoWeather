package com.rebusta.photoweather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.rebusta.photoweather.R
import com.rebusta.photoweather.activity.image.FullScreenImageActivity
import com.rebusta.photoweather.base.BaseFragment
import com.rebusta.photoweather.common.viewstate.Status
import com.rebusta.photoweather.data.local.entity.WeatherPhoto
import com.rebusta.photoweather.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private lateinit var weatherPhotosAdapter: WeatherPhotosAdapter
    override val viewModel: HomeViewModel by viewModel()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, attachToParent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setupRecyclerView()
        setupObservers()
        viewModel.getWeatherPhotoList()
    }

    private fun setupObservers() {
        viewModel.weatherPhotosResponse.observe(viewLifecycleOwner, { resp ->
            when (resp.status) {
                Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    resp.data?.let {
                        setupData(it)
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    resp.message?.let {
                        showConfirmationMessage(
                            it,
                            getString(R.string.ok)
                        )
                    }
                }
                Status.NETWORK_ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    showConfirmationMessage(
                        getString(R.string.error_occurred),
                        getString(R.string.ok)
                    )
                }
            }
        })
    }

    private fun setupData(photos: List<WeatherPhoto>) {
        if (photos.isNullOrEmpty())
            showEmptyList(true)
        else
            weatherPhotosAdapter.submitList(photos)
    }

    private fun setupRecyclerView() {
        weatherPhotosAdapter = WeatherPhotosAdapter { view, weatherPhoto, i ->
            openActivity(
                FullScreenImageActivity::class.java, bundleOf(
                    "photo_path" to weatherPhoto.photoPath
                )
            )
        }
        binding.recyclerview.adapter = weatherPhotosAdapter
    }

    private fun initViews() {
        binding.capturePhotoFab.setOnClickListener {
            navigateWithAction(HomeFragmentDirections.actionHomeFragmentToCameraFragment())
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.recyclerview.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.recyclerview.visibility = View.VISIBLE
        }
    }


}