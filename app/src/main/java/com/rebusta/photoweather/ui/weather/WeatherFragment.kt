package com.rebusta.photoweather.ui.weather

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.FileObserver
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.rebusta.photoweather.R
import com.rebusta.photoweather.base.BaseFragment
import com.rebusta.photoweather.common.BitmapUtils
import com.rebusta.photoweather.common.viewstate.Status
import com.rebusta.photoweather.databinding.FragmentWeatherBinding
import com.rebusta.photoweather.domain.model.WeatherResponse
import com.rebusta.photoweather.ui.weather.LocationManager.Companion.REQUEST_CHECK_SETTINGS
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.roundToInt

class WeatherFragment : BaseFragment<FragmentWeatherBinding, WeatherViewModel>() {

    private var observer: FileObserver? = null
    private var locationManager: LocationManager? = null
    override val viewModel: WeatherViewModel by viewModel()

    private val weatherFragmentArgs by navArgs<WeatherFragmentArgs>()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentWeatherBinding = FragmentWeatherBinding.inflate(inflater, container, attachToParent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setupObservers()
        initLocationManager()
    }

    private fun setupObservers() {
        viewModel.weatherDataResponse.observe(viewLifecycleOwner, { resp ->
            when (resp.status) {
                Status.LOADING -> showProgressDialog()
                Status.SUCCESS -> {
                    hideProgressDialog()
                    resp.data?.let {
                        setupData(it)
                    }
                }
                Status.ERROR -> {
                    hideProgressDialog()
                    resp.message?.let {
                        showConfirmationMessage(
                            it,
                            getString(R.string.ok)
                        )
                    }
                }
                Status.NETWORK_ERROR -> {
                    hideProgressDialog()
                    showConfirmationMessage(
                        getString(R.string.error_occurred),
                        getString(R.string.ok)
                    )
                }
            }
        })
    }

    private fun initViews() {
        Glide.with(this).load(weatherFragmentArgs.photoPath).placeholder(R.color.gray)
            .error(R.color.gray).into(binding.photoImv)
        binding.getWeatherDataButton.setOnClickListener {
            if (hasPermission(ACCESS_FINE_LOCATION)) {
                showProgressDialog()
                locationManager?.startLocationUpdates()
            } else {
                initLocationManager()
            }
        }
    }

    private fun initLocationManager() {
        if (hasPermission(ACCESS_FINE_LOCATION)) {
            locationManager = LocationManager(requireActivity()) {
                locationManager?.stopLocationUpdates()
                viewModel.getWeatherData(it.latitude, it.longitude)
            }
        } else {
            requestLocationPermissionLauncher.launch(ACCESS_FINE_LOCATION)
        }
    }

    private fun setupData(weatherResponse: WeatherResponse) {
        setupViews(weatherResponse)
        Timer().schedule(500) {
            val bitmap = BitmapUtils.convertViewToBitmap(binding.weatherPhotoLayout)
            observeFileChanges(weatherFragmentArgs.photoPath)
            BitmapUtils.replaceOldWithNewPhoto(bitmap, weatherFragmentArgs.photoPath)
            viewModel.saveWeatherPhoto(weatherFragmentArgs.photoPath)
        }
    }

    private fun observeFileChanges(photoPath: String) {
        observer = object : FileObserver(File(photoPath)) {
            override fun onEvent(event: Int, file: String?) {
                if (event == CLOSE_WRITE) {
                    Handler(Looper.getMainLooper()).post {
                        navigateToShareFragment(photoPath)
                    }
                }
            }
        }
        observer?.startWatching()
    }

    private fun navigateToShareFragment(photoPath: String) {
        navigateWithAction(
            WeatherFragmentDirections.actionWeatherFragmentToShareFragment(
                photoPath
            )
        )
    }

    private fun setupViews(weatherResponse: WeatherResponse) {
        binding.overlay.visibility = View.VISIBLE
        binding.locationTxv.text = String.format(
            "%s, %s",
            weatherResponse.name,
            weatherResponse.sys.country
        )
        binding.weatherStatusTxv.text = weatherResponse.weather[0].main
        binding.tempTxv.text = String.format("%s°", weatherResponse.mainItem.temp.roundToInt())
        binding.minTempTxv.text = String.format(
            "%s ° / %s °",
            weatherResponse.mainItem.tempMax.roundToInt(),
            weatherResponse.mainItem.tempMin.roundToInt()
        )
        Glide.with(this).load(weatherResponse.weather[0].getIconUrl())
            .into(binding.weatherStatusImv)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    Log.d("location", "User agreed to make required location settings changes.")
                    locationManager?.startLocationUpdates()
                }
                Activity.RESULT_CANCELED -> {
                    Log.d("location", "User chose not to make required location settings changes.")
                    hideProgressDialog()
                }
            }
        }
    }

    private val requestLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                initLocationManager()
            } else {
                showLongToast(R.string.permission_denied)
            }
        }

    override fun onPause() {
        super.onPause()
        locationManager?.stopLocationUpdates()
        observer?.startWatching()
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 2
    }
}