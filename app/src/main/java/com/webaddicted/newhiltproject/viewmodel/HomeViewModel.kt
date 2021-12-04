package com.webaddicted.newhiltproject.viewmodel

import androidx.lifecycle.MutableLiveData
import com.webaddicted.newhiltproject.data.model.UserModel
import com.webaddicted.newhiltproject.data.model.common.CommonRespo
import com.webaddicted.newhiltproject.data.model.home.AppVersionRespo
import com.webaddicted.newhiltproject.data.model.home.UserInfoRespo
import com.webaddicted.newhiltproject.data.respo.HomeRepository
import com.webaddicted.newhiltproject.utils.apiutils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: HomeRepository) :
    BaseViewModel() {
    var userInfoRespo = MutableLiveData<ApiResponse<CommonRespo<UserInfoRespo>>>()
    var appVersionRespo = MutableLiveData<ApiResponse<CommonRespo<AppVersionRespo>>>()

    fun setPrefUserInfo(respo: UserInfoRespo?) {
        val userModel = UserModel().apply {
            Email = respo?.Email
            Id = respo?.Id
            MobilePhone = respo?.MobilePhone
            Name = respo?.Name
            Username = respo?.Username
            address = respo?.address
        }
        return repo.setPrefUserInfo(userModel = userModel)
    }

    fun getPrefUserInfo(): UserModel {
        return repo.getPrefUserInfo()
    }

    val mActivity by lazy { repo.clearPrefData() }

    fun clearPref() {
        repo.clearPrefData()
    }

    fun userInfoApi() {
        userInfoRespo = MutableLiveData<ApiResponse<CommonRespo<UserInfoRespo>>>()
        repo.userInfoApi(userInfoRespo)
    }

    fun getAppVersion() {
        appVersionRespo = MutableLiveData<ApiResponse<CommonRespo<AppVersionRespo>>>()
        repo.getAppVersion(appVersionRespo)
    }

    fun setUpdateNotifyDays(days: Long) {
        repo.setUpdateNotifyDays(days)
    }

    fun getUpdateNotifyDays(): Long? {
        return repo.getUpdateNotifyDays()
    }
}