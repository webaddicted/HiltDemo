package com.webaddicted.newhiltproject.data.respo

import androidx.lifecycle.MutableLiveData
import com.webaddicted.newhiltproject.data.model.common.CommonRespo
import com.webaddicted.newhiltproject.data.model.home.AppVersionRespo
import com.webaddicted.newhiltproject.data.model.home.UserInfoRespo
import com.webaddicted.newhiltproject.utils.apiutils.ApiResponse
import com.webaddicted.newhiltproject.utils.apiutils.DataFetchCall
import kotlinx.coroutines.Deferred
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor() : BaseRepository() {
    fun userInfoApi(userInfoRepo: MutableLiveData<ApiResponse<CommonRespo<UserInfoRespo>>>) {
        object : DataFetchCall<CommonRespo<UserInfoRespo>>(userInfoRepo) {
            override fun createCallAsync(): Deferred<Response<CommonRespo<UserInfoRespo>>> {
                return apiServices.getUserInfo()
            }

            override fun shouldFetchFromDB(): Boolean {
                return false
            }

            override fun internetConnection(): Boolean {
                return networkHelper.isNetworkConnected()
            }
        }.execute()
    }

    fun getAppVersion(userInfoRepo: MutableLiveData<ApiResponse<CommonRespo<AppVersionRespo>>>) {
        object : DataFetchCall<CommonRespo<AppVersionRespo>>(userInfoRepo) {
            override fun createCallAsync(): Deferred<Response<CommonRespo<AppVersionRespo>>> {
                return apiServices.getAppVersion()
            }

            override fun shouldFetchFromDB(): Boolean {
                return false
            }

            override fun internetConnection(): Boolean {
                return networkHelper.isNetworkConnected()
            }
        }.execute()
    }
    fun setUpdateNotifyDays(days: Long) {
        spManager.setUpdateNotifyDays(days)
    }

    fun getUpdateNotifyDays(): Long? {
        return spManager.getUpdateNotifyDays()
    }
}