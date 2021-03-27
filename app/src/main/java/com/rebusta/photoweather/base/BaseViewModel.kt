package com.rebusta.photoweather.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


abstract class BaseViewModel : ViewModel(){

    val loading = MutableLiveData(false)

}