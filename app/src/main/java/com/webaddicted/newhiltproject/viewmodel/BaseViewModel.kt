package com.webaddicted.newhiltproject.viewmodel

import androidx.lifecycle.ViewModel
import com.webaddicted.newhiltproject.utils.common.NetworkHelper
import javax.inject.Inject


open class BaseViewModel : ViewModel() {
    @Inject lateinit var  networkHelper: NetworkHelper

}