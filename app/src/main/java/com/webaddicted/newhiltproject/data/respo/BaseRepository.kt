package com.webaddicted.newhiltproject.data.respo

import com.webaddicted.newhiltproject.data.model.UserModel
import com.webaddicted.newhiltproject.utils.apiutils.ApiServices
import com.webaddicted.newhiltproject.utils.common.NetworkHelper
import com.webaddicted.newhiltproject.utils.sp.PreferenceMgr
import javax.inject.Inject

open class BaseRepository @Inject constructor() {
    @Inject
    lateinit var apiServices: ApiServices

    @Inject
    lateinit var spManager: PreferenceMgr

    @Inject
    lateinit var networkHelper: NetworkHelper


    fun getPrefUserInfo(): UserModel {
        return spManager.getUserInfo()
    }

    fun setPrefUserInfo(userModel: UserModel) {
        spManager.setUserInfo(userModel = userModel)
    }

    fun clearPrefData() {
        spManager.clearPref()
    }
}