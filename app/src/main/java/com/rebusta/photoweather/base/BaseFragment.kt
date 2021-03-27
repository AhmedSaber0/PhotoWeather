package com.rebusta.photoweather.base

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.rebusta.photoweather.R
import java.io.File


abstract class BaseFragment<V : ViewBinding, VM : BaseViewModel> : Fragment() {

    protected abstract val viewModel: BaseViewModel
    protected var mActivity: BaseActivity<*, *>? = null
    lateinit var binding: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initBinding(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.mActivity = context
        }
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    fun openActivityAndClearAll(destinationActivity: Class<*>) {
        mActivity?.openActivityAndClearAll(destinationActivity)
    }

    fun showConfirmationMessage(
        titleResId: Int, messageResId: Int, posResText: Int
    ): MaterialDialog? {
        return mActivity?.showConfirmationMessage(titleResId, messageResId, posResText)
    }

    fun showConfirmationMessage(
        titleResId: Int, messageResId: Int, posResText: Int, negResText: Int,
        positiveClick: () -> Unit
    ): MaterialDialog? {
        return mActivity?.showConfirmationMessage(
            titleResId, messageResId, posResText, negResText,
            positiveClick
        )
    }

    fun showConfirmationMessage(
        titleResId: Int, messageResId: Int, posResText: Int,
        positiveClick: () -> Unit
    ): MaterialDialog? {
        return mActivity?.showConfirmationMessage(
            titleResId, messageResId, posResText,
            positiveClick
        )
    }

    fun showConfirmationMessage(
        messageResId: Int, posResText: Int
    ): MaterialDialog? {
        return mActivity?.showConfirmationMessage(messageResId, posResText)

    }

    fun showConfirmationMessage(title: String, message: String, posText: String): MaterialDialog? {
        return mActivity?.showConfirmationMessage(title, message, posText)

    }

    fun showConfirmationMessage(message: String, posText: String): MaterialDialog? {
        return mActivity?.showConfirmationMessage(message, posText)
    }

    open fun showConfirmationMessage(message: String, posText: Int): MaterialDialog? {
        return mActivity?.showConfirmationMessage(message, posText)
    }

    fun showProgressDialog() {
        mActivity?.showProgressDialog()
    }

    fun hideProgressDialog() {
        mActivity?.hideProgressDialog()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return mActivity?.hasPermission(permission) ?: false
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        mActivity?.requestPermissionsSafely(permissions, requestCode)
    }

    fun hideKeyboard() {
        mActivity?.hideKeyboard()
    }

    fun showLongToast(message: String) {
        mActivity?.showLongToast(message)
    }

    fun showShortToast(message: String) {
        mActivity?.showShortToast(message)
    }

    fun showLongToast(resourceId: Int) {
        mActivity?.showLongToast(resourceId)
    }

    fun showShortToast(resourceId: Int) {
        mActivity?.showShortToast(resourceId)
    }


    fun openActivity(destinationActivity: AppCompatActivity) {
        mActivity?.openActivity(destinationActivity)
    }

    fun openActivity(destinationActivity: Class<*>, bundle: Bundle? = null) {
        mActivity?.openActivity(destinationActivity, bundle)
    }

    fun openActivityAndFinish(destinationActivity: Class<*>) {
        mActivity?.openActivityAndFinish(destinationActivity)
    }

    fun backClicked(view: View) {
        mActivity?.backClicked(view)
    }

    internal class PageInfo {
        var page = 1
        fun nextPage() {
            page++
        }

        fun reset() {
            page = 1
        }

        val isFirstPage: Boolean
            get() = page == 1
    }

    fun navigateWithAction(action: NavDirections) {
        findNavController().navigate(action)
    }

    fun navigate(resId: Int) {
        findNavController().navigate(resId)
    }

    fun sharePhoto(photoPath: String) {
        val photoFile = File(photoPath)
        val myPhotoFileUri = FileProvider.getUriForFile(
            requireContext(),
            requireActivity().applicationContext.packageName + ".fileprovider",
            photoFile
        )
        val intent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_STREAM, myPhotoFileUri)
            this.type = "image/*"
        }
        startActivity(Intent.createChooser(intent, resources.getText(R.string.share_with)))
        }

    abstract fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): V
}
