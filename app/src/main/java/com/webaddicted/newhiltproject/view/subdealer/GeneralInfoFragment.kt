package com.webaddicted.newhiltproject.view.subdealer

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmGeneralInfoBinding
import com.webaddicted.newhiltproject.utils.apiutils.ApiResponse
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.showToast
import com.webaddicted.newhiltproject.utils.common.LocationHelper
import com.webaddicted.newhiltproject.utils.common.ValidationHelper
import com.webaddicted.newhiltproject.utils.common.gone
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.dialog.PickerDialog
import com.webaddicted.newhiltproject.view.home.HomeActivity

class GeneralInfoFragment : BaseFragment(R.layout.frm_general_info), PickerDialog.ClickListener {
    private lateinit var data: ArrayList<String>
    private var distributorName: String? = ""
    private var lat: String = ""
    private var long: String = ""
    private lateinit var mBinding: FrmGeneralInfoBinding
    private var districtList: List<String>? = null
    private var townList: List<String>? = null

    companion object {
        val TAG = GeneralInfoFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): GeneralInfoFragment {
            val fragment = GeneralInfoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmGeneralInfoBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.general_detail))
        (mActivity as HomeActivity).setActionBarBackIcon(false)
        (mActivity as HomeActivity).lockDrawer(false)
        data = AppConstant.OrderCategory.values().map { it.value } as ArrayList<String>
        districtList = AppConstant.OrderCategory.values().map { it.value } as ArrayList<String>
        townList = AppConstant.OrderCategory.values().map { it.value } as ArrayList<String>
        clickListener()
    }

    private fun clickListener() {
        mBinding.txtSubdealerName.text =
            getString(R.string.distributor_branch) + " : $distributorName"
        mBinding.txtLocation.text = "$lat\n$long"
        mBinding.switchTcsApplicable.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) mBinding.inputTcsRate.visible() else mBinding.inputTcsRate.gone()
        }
        mBinding.switchTdsApplicable.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) mBinding.inputTdsRate.visible() else mBinding.inputTdsRate.gone()
        }
        mBinding.btnNext.setOnClickListener(this)
        mBinding.btnGetLocation.setOnClickListener(this)
        mBinding.edtState.setOnClickListener(this)
        mBinding.edtDistrict.setOnClickListener(this)
        mBinding.edtTown.setOnClickListener(this)
        mBinding.edtTcsRate.setOnClickListener(this)
        mBinding.edtTdsRate.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.btn_next -> {
                if (validate()) {
                    navigateScreen(SubDealerSettingFragment.TAG)
                }
            }
            R.id.edt_state -> {
                showDialog(
                    getString(R.string.select_state),
                    data,
                    AppConstant.DialogType.STATE.value
                )
            }
            R.id.edt_district -> {
                if (!ValidationHelper.validateBlank(
                        mBinding.edtState,
                        mBinding.inputState,
                        getString(R.string.select_state)
                    )
                ) {
                    mActivity.showToast(getString(R.string.select_state))
                } else if (!districtList.isNullOrEmpty())
                    showDialog(
                        getString(R.string.select_district),
                        districtList as ArrayList<String>,
                        AppConstant.DialogType.DISTRICT.value
                    )
            }
            R.id.edt_town -> {
                if (!ValidationHelper.validateBlank(
                        mBinding.edtState,
                        mBinding.inputState,
                        getString(R.string.select_state)
                    )
                ) {
                    mActivity.showToast(getString(R.string.select_state))
                } else if (!ValidationHelper.validateBlank(
                        mBinding.edtDistrict,
                        mBinding.inputDistrict,
                        getString(R.string.select_district)
                    )
                ) {
                    mActivity.showToast(getString(R.string.select_district))
                } else if (!townList.isNullOrEmpty())
                    showDialog(
                        getString(R.string.select_town),
                        townList as ArrayList<String>,
                        AppConstant.DialogType.TOWN.value
                    )

            }
            R.id.edt_tcs_rate -> {
                showDialog(
                    getString(R.string.select_tcs_rate),
                    data,
                    AppConstant.DialogType.TCS_RATE.value
                )
            }
            R.id.edt_tds_rate -> {
                showDialog(
                    getString(R.string.select_tds_rate),
                    data,
                    AppConstant.DialogType.TDS_RATE.value
                )
            }
            R.id.btn_get_location -> {
                LocationHelper.getCurrentLocation(mActivity) { status: ApiResponse.Status, latLong: String, errorMsg: String ->
                    when (status) {
                        ApiResponse.Status.SUCCESS -> {
                            mBinding.loadingTyreIv.gone()
                            val latLongValue = latLong.split(",")
                            lat = latLongValue[0]
                            long = latLongValue[1]
                            mBinding.txtLocation.text = "$lat\n$long"
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
    }

//    private fun subDealerPicklist() {
//        if (subDealerViewModel.subDealerPickRespo.value?.data?.isSuccess == true) {
//            subDealerPickList = subDealerViewModel.subDealerPickRespo.value?.data?.respDetails
//            return
//        }
//        subDealerViewModel.subDealerPicklist()
//        subDealerViewModel.subDealerPickRespo.observe(this, {
//            handleApiRespo(
//                it,
//                mBinding.loadingTyreIv
//            ) { isFailure: Boolean, result: ApiResponse<CommonRespo<SubDealerPickListRespo>>? ->
//                if (isFailure) subDealerPicklist()
//                else {
//                    if (it.data?.isSuccess == true) {
//                        subDealerPickList = it.data.respDetails
//                        distributorName = subDealerPickList?.distributorName
//                        mBinding.txtSubdealerName.text =
//                            getString(R.string.distributor_branch) + " : ${subDealerPickList?.distributorName}"
//                    } else {
//                        it.data?.strMessage?.let { it1 ->
//                            DialogUtils.showDialog(
//                                mActivity,
//                                getString(R.string.error),
//                                it1
//                            )
//                        }
//                    }
//
//                }
//            }
//        })
//    }

//    private fun dependentPicklist(searchType: String?, searchFilter: String?) {
//        subDealerViewModel.dependentPicklist(searchType, searchFilter)
//        subDealerViewModel.dependentPicklistRespo.observe(this, {
//            handleApiRespo(
//                it,
//                mBinding.loadingTyreIv
//            ) { isFailure: Boolean, result: ApiResponse<CommonRespo<DependentPickListRespo>>? ->
//                if (isFailure) dependentPicklist(searchType, searchFilter)
//                else {
//                    if (it.data?.isSuccess == true) {
//                        when (searchType) {
//                            ApiConstant.DependentPicklistType.DISTRICT.value -> {
//                                districtList = it.data.respDetails?.dependentPicklist
//                            }
//                            ApiConstant.DependentPicklistType.TOWN.value -> {
//                                townList = it.data.respDetails?.dependentPicklist
//                            }
//                        }
//                    } else {
//                        it.data?.strMessage?.let { it1 ->
//                            DialogUtils.showDialog(
//                                mActivity,
//                                getString(R.string.error),
//                                it1
//                            )
//                        }
//                    }
//
//                }
//            }
//        })
//    }

    private fun validate(): Boolean {
        var isValid = true
        mBinding.inputPanNo.error = null
//    mBinding.inputAadharNo.error = null
        mBinding.inputGstinNo.error = null
        if (!ValidationHelper.validateBlank(
                mBinding.edtSubDealerName,
                mBinding.inputSubDealerName,
                getString(R.string.enter_name)
            )
        ) isValid = false
        if (!ValidationHelper.validateMobileNo(mBinding.edtMobile, mBinding.inputMobile)) isValid =
            false
        if (!ValidationHelper.validateEmail(mBinding.edtEmail, mBinding.inputEmail)) isValid = false

        if (!ValidationHelper.validateBlank(
                mBinding.edtAddress1,
                mBinding.inputAddress1,
                getString(R.string.enter_address)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtAddress2,
                mBinding.inputAddress2,
                getString(R.string.enter_address)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtState,
                mBinding.inputState,
                getString(R.string.select_state)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtDistrict,
                mBinding.inputDistrict,
                getString(R.string.select_district)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtTown,
                mBinding.inputTown,
                getString(R.string.select_town)
            )
        ) isValid = false
        if (!ValidationHelper.validatePostalCode(
                mBinding.edtPostalCode,
                mBinding.inputPostalCode
            )
        ) isValid = false
        val isRegChecked = mBinding.switchIsReg.isChecked
        if ((isRegChecked || !ValidationHelper.isBlank(mBinding.edtPanNo)) && !ValidationHelper.validatePanNo(
                mBinding.edtPanNo,
                mBinding.inputPanNo
            )
        ) isValid = false
//    if ((isRegChecked || !isBlank(mBinding.edtAadharNo)) && !validateAadharNo(
//        mBinding.edtAadharNo,
//        mBinding.inputAadharNo
//      )
//    ) isValid = false
        if ((isRegChecked || !ValidationHelper.isBlank(mBinding.edtGstinNo)) && !ValidationHelper.validateGSTINNo(
                mBinding.edtGstinNo,
                mBinding.inputGstinNo
            )
        ) isValid = false
        if (mBinding.switchTcsApplicable.isChecked && !ValidationHelper.validateBlank(
                mBinding.edtTcsRate,
                mBinding.inputTcsRate,
                getString(R.string.enter_tcs_rate)
            )
        ) isValid = false
        if (mBinding.switchTdsApplicable.isChecked && !ValidationHelper.validateBlank(
                mBinding.edtTdsRate,
                mBinding.inputTdsRate,
                getString(R.string.enter_tds_rate)
            )
        ) isValid = false
        return isValid
    }

    private fun showDialog(title: String, list: ArrayList<String>, reqCode: Int) {
        val dialog = PickerDialog.dialog(title, list)
        dialog.setTargetFragment(this, reqCode)
        dialog.show(mActivity.supportFragmentManager, "PickerDialog")
    }

    override fun itemClick(item: String, targetRequestCode: Int) {
        when (targetRequestCode) {
            AppConstant.DialogType.STATE.value -> {
                mBinding.edtState.setText(item)
                mBinding.edtDistrict.setText("")
                mBinding.edtTown.setText("")
            }
            AppConstant.DialogType.DISTRICT.value -> {
                mBinding.edtDistrict.setText(item)
                mBinding.edtTown.setText("")
            }
            AppConstant.DialogType.TOWN.value -> {
                mBinding.edtTown.setText(item)
            }
            AppConstant.DialogType.TCS_RATE.value -> {
                mBinding.edtTcsRate.setText(item)
            }
            AppConstant.DialogType.TDS_RATE.value -> {
                mBinding.edtTdsRate.setText(item)
            }
        }
    }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            SubDealerSettingFragment.TAG -> frm = SubDealerSettingFragment.getInstance(tag, tag)
        }
        if (frm != null) navigateFragment(R.id.container, frm, true)
    }
}
