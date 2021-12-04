package com.webaddicted.newhiltproject.view.visit.beat

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmVisitBinding
import com.webaddicted.newhiltproject.utils.apiutils.ApiResponse
import com.webaddicted.newhiltproject.utils.common.*
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.showToast
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseActivity
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.view.visit.visitdetails.VisitDetailsFragment

class VisitFragment : BaseFragment(R.layout.frm_visit) {
    private var currentLat: Double = 0.0
    private var currentLong: Double = 0.0
    private var isPending: Boolean = true
    private var visitAdapter: VisitAdapter? = null
    private lateinit var mBinding: FrmVisitBinding
    private var spinnerAdapter: ArrayAdapter<String>? = null
    private lateinit var tabTitle: List<String>
    private var routesList: ArrayList<String>? = null
    private var todayBeatData: ArrayList<String>? = null
    private var isTodayBeat: Boolean = true

    companion object {
        val TAG = VisitFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): VisitFragment {
            val fragment = VisitFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmVisitBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.visit))
        (mActivity as HomeActivity).setActionBarBackIcon(false)
        (mActivity as HomeActivity).lockDrawer(false)
        spinnerAdapter = ArrayAdapter(
            mActivity,
            R.layout.row_spinner_txt,
            routesList ?: listOf()
        )
        spinnerAdapter?.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        mBinding.routesSpinner.adapter = spinnerAdapter
        isPending = true
        routesList = AppConstant.ECatalogueVehicleType.values().map { it.name } as ArrayList<String>
        todayBeatData =
            AppConstant.ECatalogueVehicleType.values().map { it.name } as ArrayList<String>
        setTabBar()
        clickListener()
        getRoutes()
        setTodayBeatData(todayBeatData)
    }

    private fun setTabBar() {
        tabTitle = AppConstant.VisitActionTab.values().map { it.value }
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(tabTitle[0]))
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(tabTitle[1]))
        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                isPending = tab.position == 0
                todayBeatData?.let { setTodayBeatData(it) }
                GlobalUtils.logPrint(msg = "${todayBeatData?.size}")
//        viewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun clickListener() {
        mBinding.routesSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
//        val selectedItem: String = parent.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        mBinding.rgBeat.setOnCheckedChangeListener { _, checkedId ->
            val radioButton: View = mBinding.root.findViewById(checkedId)
            when (mBinding.rgBeat.indexOfChild(radioButton)) {
                0 -> {
                    isTodayBeat = true
                    mBinding.linear.gone()
                    mBinding.tabLayout.visible()
                    setTodayBeatData(todayBeatData)
                }
                1 -> {
                    isTodayBeat = false
                    mBinding.linear.visible()
                    mBinding.tabLayout.gone()
                    if (routesList != null && routesList?.size!! > 0) {
                        updateAdapter(todayBeatData)
                    } else {
                        updateAdapter(todayBeatData)
                        getRoutes()
                    }
                }
            }
        }
    }

    private fun getRoutes() {
        spinnerAdapter = ArrayAdapter(
            mActivity,
            R.layout.row_spinner_txt,
            routesList ?: listOf()
        )
        spinnerAdapter?.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        mBinding.routesSpinner.adapter = spinnerAdapter
    }


//    private fun getTodayBeats(callApi: Boolean) {
//        if (!callApi && visitViewModel.getTodayBeatRespo.value?.data?.isSuccess == true) {
//            todayBeatData = visitViewModel.getTodayBeatRespo.value?.data?.respDetails
//            setTodayBeatData(todayBeatData)
//            return
//        }
//        visitViewModel.getTodayBeatFromDb(ApiConstant.BeatType.TODAY_BEAT.value)
////    visitViewModel.getTodayBeatList(ApiConstant.BeatType.TODAY_BEAT.value)
//        visitViewModel.getTodayBeatRespo.observe(this, {
//            handleApiRespo(
//                it,
//                mBinding.loadingTyreIv
//            ) { isFailure: Boolean, result: ApiResponse<CommonListRespo<BeatRespo>>? ->
//                if (isFailure) getTodayBeats(false)
//                else {
//                    todayBeatData = result?.data?.respDetails
//                    if (isTodayBeat) {
//                        visitViewModel.addTodayVisitDB(todayBeatData)
//                        setTodayBeatData(todayBeatData)
//                    }
//                }
//            }
//        })
//    }
//
//    private fun getOtherBeats() {
//        mBinding.errorTv.gone()
//        visitViewModel.getOtherBeatList(
//            ApiConstant.BeatType.OTHER_BEAT.value,
//            beatId.toString(),
//        )
//        visitViewModel.getOtherBeatRespo.observe(this, {
//            handleApiRespo(
//                it,
//                mBinding.loadingTyreIv
//            ) { isFailure: Boolean, result: ApiResponse<CommonListRespo<BeatRespo>>? ->
//                if (isFailure) getOtherBeats()
//                else {
//                    otherBeatData = result?.data?.respDetails
//                    if (!isTodayBeat) {
//                        updateAdapter(otherBeatData)
//                    }
//                }
//            }
//        })
//    }

    private fun setTodayBeatData(todayBeatData: ArrayList<String>?) {
        updateAdapter(todayBeatData)
    }

    private fun updateAdapter(otherBeatData: ArrayList<String>?) {
        if (otherBeatData == null || otherBeatData.size == 0) {
            mBinding.errorTv.text = getString(R.string.no_records_found)
            mBinding.errorTv.visible()
        } else mBinding.errorTv.gone()
        visitAdapter =
            VisitAdapter(mActivity as BaseActivity, otherBeatData) { menuItem: String ->
                if (!networkHelper.isNetworkConnected()) {
                    DialogUtils.showDialog(
                        mActivity,
                        getString(R.string.warning),
                        getString(R.string.dialog_no_internet_msg)
                    )
                } else {
                    LocationHelper.getCurrentLocation(mActivity) { status: ApiResponse.Status, latLong: String, errorMsg: String ->
                        when (status) {
                            ApiResponse.Status.SUCCESS -> {
                                mBinding.loadingTyreIv.gone()
                                val latLongValue = latLong.split(",")
                                currentLat = latLongValue[0].toDouble()
                                currentLong = latLongValue[1].toDouble()
                                navigateScreen(VisitDetailsFragment.TAG.toString())
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
            }
        mBinding.itemRv.layoutManager = LinearLayoutManager(
            mActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        mBinding.itemRv.adapter = visitAdapter
    }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            VisitDetailsFragment.TAG -> frm = VisitDetailsFragment.getInstance()
        }
        if (frm != null) navigateFragment(R.id.container, frm, true)
    }

}
