package com.webaddicted.newhiltproject.utils.common

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.webaddicted.newhiltproject.utils.apiutils.ApiResponse
import java.util.*


object LocationHelper {
    fun getCurrentLocation(
        mActivity: Activity,
        clickListener: (status: ApiResponse.Status, latLong: String, errorMsg: String) -> Unit
    ) {
        val locationList = ArrayList<String>()
        locationList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        PermissionHelper.requestMultiplePermission(
            mActivity,
            locationList,
            object : PermissionHelper.PermissionListener {
                override fun onPermissionGranted(mCustomPermission: List<String>) {
                    isGPSEnable(mActivity, clickListener)
                }

                override fun onPermissionDenied(mCustomPermission: List<String>) {

                }
            })
    }

    private fun isGPSEnable(
        context: Activity,
        clickListener: (status: ApiResponse.Status, latLong: String, errorMsg: String) -> Unit
    ) {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val result: Task<LocationSettingsResponse> =
            LocationServices.getSettingsClient(context).checkLocationSettings(builder.build())

        result.addOnCompleteListener { task ->
            try {
                val response = task.getResult(ApiException::class.java)
                // All location settings are satisfied. The client can initialize location requests here.
//        requestCurrentLocation(context, clickListener)
                clickListener(ApiResponse.Status.LOADING, "", "")
                getLocationCurrent(context, isContinueFetch = false, clickListener)
            } catch (exception: ApiException) {
                GlobalUtils.logPrint(
                    msg =
                    "Location failed(ApiException) : $exception"
                )
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        try {
                            val resolvable: ResolvableApiException =
                                exception as ResolvableApiException
                            // Show the dialog by calling startResolutionForResult(), and check the result in onActivityResult().
                            resolvable.startResolutionForResult(
                                context,
                                LocationRequest.PRIORITY_HIGH_ACCURACY
                            )

                        } catch (e: IntentSender.SendIntentException) {
                            GlobalUtils.logPrint(
                                msg =
                                "Location failed(SendIntentException) : $e"
                            )
                            //Ignore the error.
                        } catch (e: ClassCastException) {
                            //Ignore, should be an impossible error.
                            GlobalUtils.logPrint(
                                msg =
                                "Location failed(ClassCastException): $e"
                            )
                        }
                    }
                }
            }
        }
    }


    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var wayLatitude = 0.0
    private var wayLongitude = 0.0
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    private var isContinue = true

    private fun getLocationCurrent(
        mActivity: Activity,
        isContinueFetch: Boolean = false,
        clickListener: (status: ApiResponse.Status, latLong: String, errorMsg: String) -> Unit
    ) {
        isContinue = isContinueFetch
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity)
        if (locationCallback != null)
            mFusedLocationClient?.removeLocationUpdates(locationCallback!!)
        locationRequest = LocationRequest.create()
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest?.interval = (2 * 1000).toLong() // 10 seconds

        locationRequest?.fastestInterval = (2 * 1000).toLong() // 5 seconds

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
//                if (locationResult == null) {
//                    return
//                }
                for (location in locationResult.locations) {
                    if (location != null) {
                        wayLatitude = location.latitude
                        wayLongitude = location.longitude
                        if (!isContinue) {
                            GlobalUtils.logPrint(
                                msg = "LocationCallback : !isContinue : " + String.format(
                                    Locale.US,
                                    "%s - %s",
                                    wayLatitude,
                                    wayLongitude
                                )
                            )
                            //TODO Location
                        } else {
                            clickListener(
                                ApiResponse.Status.SUCCESS,
                                "${wayLatitude},${wayLongitude}",
                                ""
                            )
                            GlobalUtils.logPrint(
                                msg = "LocationCallback : isContinue : " + String.format(
                                    Locale.US,
                                    "%s - %s",
                                    wayLatitude,
                                    wayLongitude
                                )
                            )
                            //TODO Location
//              stringBuilder.append(wayLatitude)
//              stringBuilder.append("-")
//              stringBuilder.append(wayLongitude)
//              stringBuilder.append("\n\n")
//              txtContinueLocation.setText(stringBuilder.toString())
                        }
                        if (!isContinue && mFusedLocationClient != null) {
                            locationCallback?.let { mFusedLocationClient?.removeLocationUpdates(it) }
                        }
                    }
                }
            }
        }
        getRealLocation(mActivity, mFusedLocationClient, clickListener)
    }

    @JvmStatic
    @SuppressLint("MissingPermission")
    fun getRealLocation(
        mActivity: Activity,
        mFusedLocationClient: FusedLocationProviderClient?,
        clickListener: (status: ApiResponse.Status, latLong: String, errorMsg: String) -> Unit
    ) {
        if (isContinue) {
            locationCallback?.let {
                mFusedLocationClient?.requestLocationUpdates(locationRequest!!,
                    it, null!!)
            }
        } else {
            mFusedLocationClient?.lastLocation?.addOnSuccessListener(mActivity) { location ->
                if (location != null) {
                    wayLatitude = location.latitude
                    wayLongitude = location.longitude
                    clickListener(
                        ApiResponse.Status.SUCCESS,
                        "${wayLatitude},${wayLongitude}",
                        ""
                    )
                    GlobalUtils.logPrint(
                        msg = "addOnSuccessListener : " + String.format(
                            Locale.US,
                            "%s - %s",
                            wayLatitude,
                            wayLongitude
                        )
                    )
                    callRequest()
//          GlobalUtils.delay(2){
//            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
//          }
                } else {
                    mFusedLocationClient.requestLocationUpdates(
                        locationRequest!!,
                        locationCallback!!,
                        null!!
                    )
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun callRequest() {
        mFusedLocationClient?.requestLocationUpdates(locationRequest!!, locationCallback!!, null!!)
    }


//  @SuppressLint("MissingPermission")
//  private fun requestCurrentLocation(
//    activity: Activity,
//    clickListener: (status: ApiResponse.Status, latLong: String, errorMsg: String) -> Unit
//  ) {
//    Log.d("TAG", "requestCurrentLocation()")
//    clickListener(ApiResponse.Status.LOADING, "", "")
////    if (activity.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//    // Returns a single current location fix on the device. Unlike getLastLocation() that
//    // returns a cached location, this method could cause active location computation on the
//    // device. A single frescheckSelfPermissionh location will be returned if the device location can be
//    // determined within reasonable time (tens of seconds), otherwise null will be returned.
//    //
//    // Both arguments are required.
//    // PRIORITY type is self-explanatory. (Other options are PRIORITY_BALANCED_POWER_ACCURACY,
//    // PRIORITY_LOW_POWER, and PRIORITY_NO_POWER.)
//    // The second parameter, [CancellationToken] allows the activity to cancel the request
//    // before completion.
//    val currentLocationTask: Task<Location> =
//      LocationServices.getFusedLocationProviderClient(activity).getCurrentLocation(
//        LocationRequest.PRIORITY_HIGH_ACCURACY,
//        CancellationTokenSource().token
//      )
//
//    currentLocationTask.addOnCompleteListener { task: Task<Location> ->
//      val result = if (task.isSuccessful && task.result != null) {
//        val result: Location = task.result
//        "Location (success): ${result.latitude}, ${result.longitude}"
//        clickListener(
//          ApiResponse.Status.SUCCESS,
//          "${result.latitude},${result.longitude}",
//          ""
//        )
//      } else {
//        val exception = task.exception
//        "Location (failure): $exception"
//        clickListener(ApiResponse.Status.ERROR, "", exception.toString())
//      }
//      Log.d("TAG", "getCurrentLocation() result: $result")
//    }
//  }


}