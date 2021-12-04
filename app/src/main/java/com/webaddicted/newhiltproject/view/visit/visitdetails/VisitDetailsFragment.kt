package com.webaddicted.newhiltproject.view.visit.visitdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmVisitDetailsBinding
import com.webaddicted.newhiltproject.utils.apiutils.ApiResponse
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.showToast
import com.webaddicted.newhiltproject.utils.common.LocationHelper
import com.webaddicted.newhiltproject.utils.common.gone
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.dialog.BadgeDialog
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.view.visit.order.CollectionFragment
import com.webaddicted.newhiltproject.view.visit.order.FocusOrderFragment
import com.webaddicted.newhiltproject.view.visit.orderhistory.OrderHistoryListFragment
import com.webaddicted.newhiltproject.view.visit.other.FeedbackFragment
import com.webaddicted.newhiltproject.view.visit.other.SurveyFragment
import com.webaddicted.newhiltproject.view.visit.other.UpdateLocationFragment
import com.webaddicted.newhiltproject.view.visit.outlet.OutletFragment

class VisitDetailsFragment : BaseFragment(R.layout.frm_visit_details) {
    private var lat: Double = 0.0
    private var long: Double = 0.0

    private lateinit var mBinding: FrmVisitDetailsBinding
    private lateinit var visitDetailsAdapter: VisitDetailsAdapter
    private var visitDetailsList: ArrayList<String>? = null

    companion object {
        val TAG = VisitDetailsFragment::class.qualifiedName
        fun getInstance(): VisitDetailsFragment {
            val bundle = Bundle()
            val fragment = VisitDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmVisitDetailsBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.retailer_detail))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        setUi()

    }

    private fun setUi() {
        visitDetailsList =
            AppConstant.VisitDetailsItem.values().map { it.value } as ArrayList<String>
        visitDetailsAdapter =
            VisitDetailsAdapter(
                "logo",
                visitDetailsList
            ) { menuItem: String -> onItemClicked(menuItem) }
        mBinding.itemRv.run {
            layoutManager = GridLayoutManager(mActivity, 2)
            adapter = visitDetailsAdapter
        }
    }

    private fun onItemClicked(menuItem: String) {
        when (menuItem) {
            AppConstant.VisitDetailsItem.ORDER_BOOKING.value -> {
                navigateScreen(FocusOrderFragment.TAG)
            }
            AppConstant.VisitDetailsItem.SALES_RETURN.value -> {
            }
            AppConstant.VisitDetailsItem.COLLECTION.value -> {
//        mActivity.showToast(getString(R.string.comming_soon))
                navigateScreen(CollectionFragment.TAG)
            }
            AppConstant.VisitDetailsItem.SURVEY.value -> {
                navigateScreen(SurveyFragment.TAG)
            }
            AppConstant.VisitDetailsItem.OUTLET_DASHBOARD.value -> {
                navigateScreen(OutletFragment.TAG)
            }
            AppConstant.VisitDetailsItem.UPDATE_LOCATION.value -> {
                navigateScreen(UpdateLocationFragment.TAG)
            }
            AppConstant.VisitDetailsItem.CHECK_OUT.value -> {
                requestLocation(AppConstant.VisitDetailsItem.CHECK_OUT.value)
            }
            AppConstant.VisitDetailsItem.FEEDBACK_REMARK.value -> {
                navigateScreen(FeedbackFragment.TAG)
            }
            AppConstant.VisitDetailsItem.NAVIGATION.value -> {
                navigateOnMap()
            }
            AppConstant.VisitDetailsItem.ORDER_HISTORY.value -> {
                navigateScreen(OrderHistoryListFragment.TAG)
            }
            AppConstant.VisitDetailsItem.BADGE.value -> {
                val dialog = BadgeDialog.dialog("catalog_car")
                dialog.show(parentFragmentManager, dialog.tag)
            }
        }
    }

    private fun navigateOnMap() {
        if (lat > 0 && long > 0) {
            val sourceLat = lat
            val sourceLong = long
            val destLat = "26.922070"
            val destLong = "75.778885"
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=$sourceLat,$sourceLong&daddr=$destLat,$destLong")
            )
            startActivity(intent)
        } else {
            requestLocation(AppConstant.VisitDetailsItem.NAVIGATION.value)
        }
    }

    private fun requestLocation(reqType: String) {
        LocationHelper.getCurrentLocation(mActivity) { status: ApiResponse.Status, latLong: String, errorMsg: String ->
            when (status) {
                ApiResponse.Status.SUCCESS -> {
                    mBinding.loadingTyreIv.gone()
                    val latLongValue = latLong.split(",")
                    lat = latLongValue[0].toDouble()
                    long = latLongValue[1].toDouble()
//          mActivity.showToast(latLong)
                    when {
                        AppConstant.VisitDetailsItem.SURVEY.value == reqType ->
                            navigateScreen(SurveyFragment.TAG)
                        AppConstant.VisitDetailsItem.CHECK_OUT.value == reqType -> checkIn()
                        AppConstant.VisitDetailsItem.NAVIGATION.value == reqType -> navigateOnMap()
                    }
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

    private fun checkIn() {
        mActivity.showToast("Check In")
    }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            FocusOrderFragment.TAG -> frm = FocusOrderFragment.getInstance()
            CollectionFragment.TAG -> frm = CollectionFragment.getInstance()
            SurveyFragment.TAG -> frm = SurveyFragment.getInstance(lat, long)
            OutletFragment.TAG -> frm = OutletFragment.getInstance()
            UpdateLocationFragment.TAG -> frm = UpdateLocationFragment.getInstance()
            FeedbackFragment.TAG -> frm = FeedbackFragment.getInstance()
            OrderHistoryListFragment.TAG -> frm = OrderHistoryListFragment.getInstance()
        }
        if (frm != null) navigateFragment(R.id.container, frm, true)
    }
}