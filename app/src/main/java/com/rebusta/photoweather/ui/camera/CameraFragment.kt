package com.rebusta.photoweather.ui.camera

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.rebusta.photoweather.base.BaseFragment
import com.rebusta.photoweather.R
import com.rebusta.photoweather.databinding.FragmentCameraBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraFragment : BaseFragment<FragmentCameraBinding, CameraViewModel>() {

    private var currentPhotoPath: String? = null
    override val viewModel: CameraViewModel by viewModel()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentCameraBinding = FragmentCameraBinding.inflate(inflater, container, attachToParent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.capturePhotoButton.setOnClickListener {
            if (checkPermission())
                dispatchTakePictureIntent()
            else
                requestCameraPermissionLauncher.launch(CAMERA)
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            activity?.packageManager?.let {
                takePictureIntent.resolveActivity(it)?.also {
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        showLongToast(R.string.error_creating_file)
                        null
                    }
                    photoFile?.also { file ->
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(),
                            requireActivity().applicationContext.packageName + ".fileprovider",
                            file
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startCameraIntentForResult.launch(takePictureIntent)
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private val startCameraIntentForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                navigateWithAction(
                    CameraFragmentDirections.actionCameraFragmentToWeatherFragment(
                        currentPhotoPath ?: ""
                    )
                )
            }
        }

    private fun checkPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(requireContext(), CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            requireContext(),
            READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                requestStoragePermissionLauncher.launch(READ_EXTERNAL_STORAGE)
            } else {
                showLongToast(R.string.permission_denied)
            }
        }

    private val requestStoragePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                dispatchTakePictureIntent()
            } else {
                showLongToast(R.string.permission_denied)
            }
        }
}