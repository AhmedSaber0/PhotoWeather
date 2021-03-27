package com.rebusta.photoweather.base

import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.rebusta.photoweather.R
import java.util.*
import kotlin.concurrent.schedule


abstract class BaseActivity<V : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

    lateinit var binding: V
    protected abstract val viewModel: BaseViewModel
    protected lateinit var activity: AppCompatActivity
    protected lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        binding = initBinding()
        setContentView(binding.root)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    fun openActivity(destinationActivity: AppCompatActivity) {
        val mainIntent = Intent(activity, destinationActivity::class.java)
        startActivity(mainIntent)
    }

    fun openActivity(destinationActivity: Class<*>, bundle: Bundle? = null) {
        val mainIntent = Intent(activity, destinationActivity)
        bundle?.let { mainIntent.putExtras(it) }
        startActivity(mainIntent)
    }

    fun openActivityAndFinish(destinationActivity: Class<*>, bundle: Bundle? = null) {
        val mainIntent = Intent(activity, destinationActivity)
        bundle?.let { mainIntent.putExtras(it) }
        startActivity(mainIntent)
        finish()
    }

    fun openActivityAndClearAll(destinationActivity: Class<*>) {
        val i = Intent(activity, destinationActivity)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }

    fun openActivityWithDelay(destinationActivity: Class<*>, delay: Long) {
        Timer().schedule(delay = delay) {
            openActivityAndFinish(destinationActivity)
        }
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showProgressDialog() {
        hideProgressDialog()
        val alertDialogBuilder = AlertDialog.Builder(this).setCancelable(false)
        alertDialogBuilder.setView(R.layout.layout_progress)
        progressDialog = alertDialogBuilder.create()
        progressDialog.window?.setBackgroundDrawableResource(R.color.transparent)
        progressDialog.show()
    }

    fun hideProgressDialog() {
        if (::progressDialog.isInitialized && progressDialog.isShowing)
            progressDialog.dismiss()
    }

    fun showConfirmationMessage(
        titleResId: Int, messageResId: Int, posResText: Int
    ): MaterialDialog? {
        return MaterialDialog(this).show {
            title(titleResId)
            message(messageResId)
            positiveButton(posResText)
        }
    }

    fun showConfirmationMessage(
        titleResId: Int, messageResId: Int, posResText: Int, negResText: Int,
        positiveClick: () -> Unit
    ): MaterialDialog? {
        return MaterialDialog(this).show {
            title(titleResId)
            message(messageResId)
            positiveButton(posResText) {
                positiveClick.invoke()
            }
            negativeButton(negResText)
        }
    }

    fun showConfirmationMessage(
        titleResId: Int, messageResId: Int, posResText: Int, negResText: Int,
        click: (Boolean) -> Unit
    ): MaterialDialog? {
        return MaterialDialog(this).show {
            title(titleResId)
            message(messageResId)
            positiveButton(posResText) {
                click.invoke(true)
            }
            negativeButton(negResText){
                click.invoke(false)
            }
        }
    }

    fun showConfirmationMessage(
        titleResId: Int, messageResId: Int, posResText: Int,
        positiveClick: () -> Unit
    ): MaterialDialog? {
        return MaterialDialog(this).show {
            title(titleResId)
            message(messageResId)
            positiveButton(posResText) {
                positiveClick.invoke()
            }
        }
    }

    fun showConfirmationMessage(
        messageResId: Int, posResText: Int
    ): MaterialDialog? {
        return MaterialDialog(this).show {
            message(messageResId)
            positiveButton(posResText)
        }
    }

    fun showConfirmationMessage(title: String, message: String, posText: String): MaterialDialog? {
        return MaterialDialog(this).show {
            title(text = title)
            message(text = message)
            positiveButton(text = posText)
        }
    }

    fun showConfirmationMessage(message: String, posText: String): MaterialDialog? {
        return MaterialDialog(this).show {
            message(text = message)
            positiveButton(text = posText)
        }
    }

    open fun showConfirmationMessage(message: String, posText: Int): MaterialDialog? {
        return MaterialDialog(this).show {
            message(text = message)
            positiveButton(posText)
        }
    }

    fun showConfirmationMessage(
        messageResId: Int, posResText: Int,
        positiveClick: () -> Unit
    ): MaterialDialog {
        return MaterialDialog(this).show {
            message(messageResId)
            positiveButton(posResText) {
                positiveClick.invoke()
            }
        }
    }

    fun showLongToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    fun showShortToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(resourceId: Int) {
        Toast.makeText(activity, resourceId, Toast.LENGTH_LONG).show()
    }

    fun showShortToast(resourceId: Int) {
        Toast.makeText(activity, resourceId, Toast.LENGTH_SHORT).show()
    }

    fun backClicked(view: View) {
        super.onBackPressed()
    }

    abstract fun initBinding(): V

}
