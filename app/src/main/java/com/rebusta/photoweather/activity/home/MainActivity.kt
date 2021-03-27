package com.rebusta.photoweather.activity.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.rebusta.photoweather.R
import com.rebusta.photoweather.base.BaseActivity
import com.rebusta.photoweather.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, HomeViewModel>() {

    override val viewModel by viewModel<HomeViewModel>()

    override fun initBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        if (navHostFragment != null) {
            val fragment: Fragment = navHostFragment.childFragmentManager.fragments[0]
            fragment.onActivityResult(requestCode, resultCode, data)
        }

    }
}