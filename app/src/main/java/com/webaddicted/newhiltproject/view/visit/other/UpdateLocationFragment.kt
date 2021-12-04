package com.webaddicted.newhiltproject.view.visit.other

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmUpdateLocationBinding
import com.webaddicted.newhiltproject.utils.apiutils.ApiResponse
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.showToast
import com.webaddicted.newhiltproject.utils.common.LocationHelper
import com.webaddicted.newhiltproject.utils.common.gone
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.view.splash.UserTypeFragment

class UpdateLocationFragment : BaseFragment(R.layout.frm_update_location) {
    private var lat: Double = 0.0
    private var long: Double = 0.0
    private lateinit var mBinding: FrmUpdateLocationBinding

    companion object {
        val TAG = UpdateLocationFragment::class.qualifiedName
        fun getInstance(): UpdateLocationFragment {
            val bundle = Bundle()
            val fragment = UpdateLocationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmUpdateLocationBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.update_location))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        setUi()
        clickListener()
    }


    private fun setUi() {
        mBinding.loadingTyreIv.visible()
        LocationHelper.getCurrentLocation(mActivity) { status: ApiResponse.Status, latLong: String, errorMsg: String ->
            when (status) {
                ApiResponse.Status.SUCCESS -> {
                    mBinding.btnSubmit.text = getString(R.string.sumbit)
                    mBinding.loadingTyreIv.gone()
                    mBinding.imgLoc.setColorFilter(ContextCompat.getColor(mActivity, R.color.green))
                    val latLongValue = latLong.split(",")
                    lat = latLongValue[0].toDouble()
                    long = latLongValue[1].toDouble()
                    mBinding.txtLat.text = "Latitude : $lat"
                    mBinding.txtLong.text = "Longitude : $long"
//          mActivity.showToast("$latLong")
                }
                ApiResponse.Status.ERROR -> {
                    mBinding.loadingTyreIv.gone()
                    mActivity.showToast("$latLong : Error : $errorMsg")
                }
                else -> {
                    mBinding.loadingTyreIv.visible()
                }
            }
        }

    }

    private fun clickListener() {
        mBinding.btnSubmit.setOnClickListener(this)
        mBinding.errorTv.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.btn_submit,
            R.id.errorTv -> {
                mActivity.onBackPressed()
            }
        }
    }


//      private fun submitFeedback() {
//            val req = UpdateLocationReq(beatData?.subDealerId, "$lat", "$long")
//            visitViewModel.updateLocation(req)
//            visitViewModel.updateLocationRespo.observe(this, {
//                  handleApiRespo(
//                    it,
//                    mBinding.loadingTyreIv
//                  ) { isFailure: Boolean, result: ApiResponse<CommonRespo<Boolean>>? ->
//                    if (isFailure) submitFeedback()
//                    else {
//                      if (it.data?.isSuccess == true) {
//                        it.data.strMessage?.let { it1 -> mActivity.showToast(it1) }
//                        activity?.onBackPressed()
//                      } else {
//                        it.data?.strMessage?.let { it1 ->
//                          DialogUtils.showDialog(
//                            mActivity,
//                            getString(R.string.error),
//                            it1
//                          )
//                        }
//                      }
//                    }
//                  }
//                })
//          }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            UserTypeFragment.TAG -> frm = UserTypeFragment.getInstance(Bundle())
//      ZoomImageFrm.TAG -> frm = ZoomImageFrm.getInstance(bundle)
        }
        if (frm != null) navigateFragment(R.id.container, frm, false)
    }
}