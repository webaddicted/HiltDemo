package com.webaddicted.newhiltproject.view.home

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.webaddicted.newhiltproject.BuildConfig
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.data.model.common.CommonRespo
import com.webaddicted.newhiltproject.data.model.home.AppVersionRespo
import com.webaddicted.newhiltproject.databinding.FrmHomeBinding
import com.webaddicted.newhiltproject.utils.apiutils.ApiResponse
import com.webaddicted.newhiltproject.utils.common.GlobalUtils
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.showToast
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.dashboard.*
import com.webaddicted.newhiltproject.view.dialog.UpdateAppDialog
import com.webaddicted.newhiltproject.viewmodel.HomeViewModel
import java.util.*

class HomeFragment : BaseFragment(R.layout.frm_home) {
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var mBinding: FrmHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    companion object {
        val TAG = HomeFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): HomeFragment {
            val fragment = HomeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmHomeBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.home))
        (mActivity as HomeActivity).setActionBarBackIcon(false)
        (mActivity as HomeActivity).lockDrawer(false)
        setUi()
//        getAppVersion()
//    GlobalUtils.logPrint(msg = "getCurrentDate ${GlobalUtils.getCurrentDate()}")
    }

    private fun setUi() {
        val data = AppConstant.HomeItem.values().map { it.value }
        homeAdapter = HomeAdapter(data) { menuItem: String -> onItemClicked(menuItem) }
        mBinding.itemRv.run {
            layoutManager = GridLayoutManager(mActivity, 2)
            adapter = homeAdapter
        }
    }

    private fun onItemClicked(menuItem: String) {
        when (menuItem) {
            AppConstant.HomeItem.MTD_DASHBOARD.value -> {
                navigateScreen(MTDDashboardFragment.TAG)
            }
            AppConstant.HomeItem.RETAILERS_L3M.value -> {
                navigateScreen(L3MRetailersFragment.TAG)
            }
            AppConstant.HomeItem.RETAILER_CREDIT.value -> {
                navigateScreen(RetailerCreditFragment.TAG)
            }
            AppConstant.HomeItem.L3M_TOP_RETAILERS.value -> {
                navigateScreen(TopL3MRetailersFragment.TAG)
            }
            AppConstant.HomeItem.CATEGORY_WISE_QUANTITY.value -> {
                mActivity.showToast(getString(R.string.comming_soon))
            }
            AppConstant.HomeItem.RETAILERS_MONTHLY_SALES.value -> {
                mActivity.showToast(getString(R.string.comming_soon))
            }
            AppConstant.HomeItem.DAY_SUMMARY.value -> {
                navigateScreen(DaySummaryFragment.TAG)
            }
        }
//            mActivity.showToast(getString(R.string.comming_soon))
    }

    private fun getAppVersion() {
        if (homeViewModel.appVersionRespo.value?.data?.isSuccess == true) {
            return
        }
        homeViewModel.getAppVersion()
        homeViewModel.appVersionRespo.observe(this, {
            handleApiRespo(
                it,
                mBinding.loadingTyreIv
            ) { isFailure: Boolean, result: ApiResponse<CommonRespo<AppVersionRespo>>? ->
                if (isFailure) getAppVersion()
                else {
                    chkVersion(result?.data?.respDetails)
//          homeViewModel.setPrefUserInfo(it.data?.respDetails)
                    GlobalUtils.logPrint(
                        HomeActivity.TAG,
                        "Check App Version ${result?.data.toString()} "
                    )
                }
            }
        })
    }

    private fun chkVersion(versionDetails: AppVersionRespo?) {
//    versionDetails?.minVersion = "1.0.0"
//    versionDetails?.preferredVersion = "1.0.1"
        when {
            GlobalUtils.versionCompare(versionDetails?.minVersion, BuildConfig.VERSION_NAME) -> {
                when {
                    GlobalUtils.versionCompare(
                        versionDetails?.preferredVersion,
                        BuildConfig.VERSION_NAME
                    ) -> {
                        //check for other disclaimer/broadcast
                        //Do Nothing
                    }
                    else -> {
                        //ask for update
                        if (homeViewModel.getUpdateNotifyDays() == 0L || hasWeekPassed()) {
                            //Non-mandatory updates should only be shown once in a week.
                            showUpdateDialog(
                                versionDetails?.message,
                                true,
                                versionDetails?.preferredVersion
                            )
                            homeViewModel.setUpdateNotifyDays(Calendar.getInstance().timeInMillis)
                        } else {
                            //check for other disclaimer/broadcast
                            //Do Nothing
                        }
                    }
                }
            }
            else -> {
                //force update
                showUpdateDialog(versionDetails?.message, false, versionDetails?.minVersion)
            }
        }
    }

    private fun showUpdateDialog(message: String?, isSkip: Boolean, preferredVersion: String?) {
        val dialog = UpdateAppDialog.dialog(message, isSkip, preferredVersion)
        dialog.isCancelable = false
        dialog.show(mActivity.supportFragmentManager, dialog.tag)
    }

    private fun hasWeekPassed(): Boolean {
        val todaydt = Calendar.getInstance()
        return GlobalUtils.getDiffInDays(
            todaydt.timeInMillis,
            homeViewModel.getUpdateNotifyDays()
        ) > 7
    }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            MTDDashboardFragment.TAG -> frm = MTDDashboardFragment.getInstance()
            DaySummaryFragment.TAG -> frm = DaySummaryFragment.getInstance()
            L3MRetailersFragment.TAG -> frm = L3MRetailersFragment.getInstance()
            TopL3MRetailersFragment.TAG -> frm = TopL3MRetailersFragment.getInstance()
            RetailerCreditFragment.TAG -> frm = RetailerCreditFragment.getInstance()
        }
        if (frm != null) navigateFragment(R.id.container, frm, true)
    }
}