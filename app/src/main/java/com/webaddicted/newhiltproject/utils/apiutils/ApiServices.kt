package com.webaddicted.newhiltproject.utils.apiutils

import com.webaddicted.newhiltproject.data.model.common.CommonRespo
import com.webaddicted.newhiltproject.data.model.home.AppVersionRespo
import com.webaddicted.newhiltproject.data.model.home.UserInfoRespo
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    @GET("services/apexrest/userInfoDMS")
    fun getUserInfo(): Deferred<Response<CommonRespo<UserInfoRespo>>>

    @GET("services/apexrest/MobileVersionDMS")
    fun getAppVersion(): Deferred<Response<CommonRespo<AppVersionRespo>>>

//      @POST("services/apexrest/visitUtilities")
//      fun getBeatList(
//          @Header("Authorization") oAuth: String,
//          @Body req: CommonReq<BeatReq>
//      ): Deferred<Response<CommonListRespo<BeatRespo>>>

//      @GET("services/apexrest/visitUtilities")
//      fun getBeatRoutesList(
//            @Header("Authorization") oAuth: String,
//        @Query("queryParam") queryParam: String
//      ): Deferred<Response<CommonListRespo<RoutesRespo>>>

//      @Multipart
//      @POST
//      fun surveyImgUpload(
//          @Url url: String,
//          @Part("DSE_code") DSE_code: RequestBody?,
//          @Part("DSE_name") DSE_name: RequestBody?,
//          @Part("Sub_dealer_name") subDealerName: RequestBody?,
//          @Part("Sub_dealer_code") subDealerCode: RequestBody?,
//          @Part("Latitude") latitude: RequestBody?,
//          @Part("Longitude") longitude: RequestBody?,
//          @Part("check_in_time") check_in_time: RequestBody?,
//          @Part file1: MultipartBody.Part
//      ): Deferred<Response<SurveyImgUploadRespo>>
}


