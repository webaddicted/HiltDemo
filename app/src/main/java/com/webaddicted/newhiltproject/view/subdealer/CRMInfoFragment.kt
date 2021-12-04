package com.webaddicted.newhiltproject.view.subdealer

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmCrmInfoBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils
import com.webaddicted.newhiltproject.utils.common.ValidationHelper
import com.webaddicted.newhiltproject.utils.common.gone
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.dialog.PickerDialog
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.viewmodel.HomeViewModel

class CRMInfoFragment : BaseFragment(R.layout.frm_crm_info), PickerDialog.ClickListener {
    private lateinit var data: ArrayList<String>
    private lateinit var mBinding: FrmCrmInfoBinding
    private val subDealerViewModel: HomeViewModel by viewModels()
    private var districtList: List<String>? = null
    private var townList: List<String>? = null

    companion object {
        val TAG = CRMInfoFragment::class.qualifiedName
        const val SUB_DEALER = "Sub Dealer"
        const val SUB_DEALER_REQ = "Sub Dealer REQ"
        fun getInstance(item: String?, req: String?): CRMInfoFragment {
            val bundle = Bundle()
            bundle.putSerializable(SUB_DEALER, item)
            bundle.putSerializable(SUB_DEALER_REQ, req)
            val fragment = CRMInfoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmCrmInfoBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.crm_info))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        clickListener()
        data = AppConstant.AccountType.values().map { it.name } as ArrayList<String>
    }

    private fun clickListener() {
        mBinding.switchMarried.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) mBinding.inputMarraigeAnniversary.visible() else mBinding.inputMarraigeAnniversary.gone()
        }
        mBinding.btnNext.setOnClickListener(this)
        mBinding.edtDob.setOnClickListener(this)
        mBinding.edtMarraigeAnniversary.setOnClickListener(this)
        mBinding.edtState.setOnClickListener(this)
        mBinding.edtPreferredLanguage.setOnClickListener(this)
        mBinding.edtOtherBusiness.setOnClickListener(this)
        mBinding.edtWeekOff.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.btn_next -> {
                if (validate()) {
                    navigateScreen(OtpFragment.TAG)
                }
            }
            R.id.edt_dob -> {
                GlobalUtils.getDate(mActivity, mBinding.edtDob)
            }
            R.id.edt_marraige_anniversary -> {
                GlobalUtils.getDate(mActivity, mBinding.edtMarraigeAnniversary)
            }

            R.id.edt_state -> {
                showDialog(
                    getString(R.string.select_state),
                    data,
                    AppConstant.DialogType.STATE.value
                )
            }
            R.id.edt_preferred_language -> {
                showDialog(
                    getString(R.string.select_language),
                    data,
                    AppConstant.DialogType.PREFERRED_LANGUAGE.value
                )
            }

            R.id.edt_other_business -> {
                showDialog(
                    getString(R.string.select_other_business),
                    data,
                    AppConstant.DialogType.OTHER_BUSINESS.value
                )
            }

            R.id.edt_week_off -> {
                showDialog(
                    getString(R.string.select_week_off),
                    AppConstant.WeekOff.values().map { it.value } as ArrayList<String>,
                    AppConstant.DialogType.WEEKLY_OFF.value)
            }
        }
    }

//      private fun dependentPicklist(searchType: String?, searchFilter: String?) {
//            subDealerViewModel.dependentPicklist(searchType, searchFilter)
//            subDealerViewModel.dependentPicklistRespo.observe(this, {
//                  handleApiRespo(
//                    it,
//                    mBinding.loadingTyreIv
//                  ) { isFailure: Boolean, result: ApiResponse<CommonRespo<DependentPickListRespo>>? ->
//                    if (isFailure) dependentPicklist(searchType, searchFilter)
//                    else {
//                      if (it.data?.isSuccess == true) {
//                        when (searchType) {
//                          ApiConstant.DependentPicklistType.DISTRICT.value -> {
//                            districtList = it.data.respDetails?.dependentPicklist
//                          }
//                          ApiConstant.DependentPicklistType.TOWN.value -> {
//                            townList = it.data.respDetails?.dependentPicklist
//                          }
//                        }
//                      } else {
//                        it.data?.strMessage?.let { it1 ->
//                          DialogUtils.showDialog(
//                            mActivity,
//                            getString(R.string.error),
//                            it1
//                          )
//                        }
//                      }
//
//                    }
//                  }
//                })
//          }


    private fun validate(): Boolean {
        var isValid = true
        if (!ValidationHelper.validateBlank(
                mBinding.edtFirstName,
                mBinding.inputFirstName,
                getString(R.string.enter_first_name)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtLastName,
                mBinding.inputLastName,
                getString(R.string.enter_last_name)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtDob,
                mBinding.inputDob,
                getString(R.string.select_dob)
            )
        ) isValid = false
        if (mBinding.switchMarried.isChecked && !ValidationHelper.validateBlank(
                mBinding.edtMarraigeAnniversary,
                mBinding.inputMarraigeAnniversary,
                getString(R.string.select_marriage_anniversary)
            )
        ) isValid = false
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
                mBinding.edtHomeCity,
                mBinding.inputHomeCity,
                getString(R.string.enter_home_city)
            )
        ) isValid = false
        if (!ValidationHelper.validatePostalCode(
                mBinding.edtPostalCode,
                mBinding.inputPostalCode
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtPreferredLanguage,
                mBinding.inputPreferredLanguage,
                getString(R.string.select_language)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtOtherBusiness,
                mBinding.inputOtherBusiness,
                getString(R.string.select_other_business)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtShareTotalBusiness,
                mBinding.inputShareTotalBusiness,
                getString(R.string.select_share_total_business)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtWeekOff,
                mBinding.inputWeekOff,
                getString(R.string.select_week_off)
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
            }
            AppConstant.DialogType.PREFERRED_LANGUAGE.value -> {
                mBinding.edtPreferredLanguage.setText(item)
            }
            AppConstant.DialogType.OTHER_BUSINESS.value -> {
                mBinding.edtOtherBusiness.setText(item)
            }
            AppConstant.DialogType.WEEKLY_OFF.value -> {
                mBinding.edtWeekOff.setText(item)
            }
        }
    }


    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            OtpFragment.TAG -> frm = OtpFragment.getInstance(tag)
        }
        if (frm != null) navigateFragment(R.id.container, frm, true)
    }
}
