package com.webaddicted.newhiltproject.view.subdealer

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmSubdealerSettingBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.showToast
import com.webaddicted.newhiltproject.utils.common.ValidationHelper
import com.webaddicted.newhiltproject.utils.common.gone
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.dialog.PickerDialog
import com.webaddicted.newhiltproject.view.home.HomeActivity


class SubDealerSettingFragment : BaseFragment(R.layout.frm_subdealer_setting),
    PickerDialog.ClickListener {
    private lateinit var data: ArrayList<String>
    private var deliveryRouteList: List<String>? = null
    private var salesRouteList: List<String>? = null
    private lateinit var mBinding: FrmSubdealerSettingBinding

    companion object {
        val TAG = SubDealerSettingFragment::class.qualifiedName
        const val SUB_DEALER = "Sub Dealer"
        const val SUB_DEALER_REQ = "Sub Dealer REQ"

        fun getInstance(
            item: String?,
            req: String?
        ): SubDealerSettingFragment {
            val bundle = Bundle()
            bundle.putSerializable(SUB_DEALER, item)
            bundle.putSerializable(SUB_DEALER_REQ, req)

            val fragment = SubDealerSettingFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmSubdealerSettingBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.setting_detail))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        data = AppConstant.AccountType.values().map { it.value } as ArrayList<String>
        deliveryRouteList = AppConstant.AccountType.values().map { it.value } as ArrayList<String>
        salesRouteList = AppConstant.AccountType.values().map { it.value } as ArrayList<String>

        clickListener()
        setUi()
    }

    private fun setUi() {
        mBinding.edtSalesmanName.setText(data[0])
        mBinding.edtCreditCash.setText(getString(R.string.credit))
    }

    private fun clickListener() {
        mBinding.btnNext.setOnClickListener(this)
//    mBinding.edtParent.setOnClickListener(this)
        mBinding.edtCreditCash.setOnClickListener(this)
        mBinding.edtSalesmanName.setOnClickListener(this)
        mBinding.edtSalesRoute.setOnClickListener(this)
        mBinding.edtDeliveryBoy.setOnClickListener(this)
        mBinding.edtDeliveryRoute.setOnClickListener(this)
        mBinding.edtCategory.setOnClickListener(this)
        mBinding.edtChannel.setOnClickListener(this)
        mBinding.edtGroup.setOnClickListener(this)
        mBinding.edtClasses.setOnClickListener(this)
        mBinding.switchIsCreditLimit.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) mBinding.inputCreditLimit.visible() else mBinding.inputCreditLimit.gone()
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.btn_next -> {
                if (validate()) {
                    navigateScreen(KycFragment.TAG)
                }
            }
            R.id.edt_credit_cash -> {
                showDialog(
                    getString(R.string.select_credit_cash_van), data,
                    AppConstant.DialogType.CREDIT_SALES.value
                )
            }
            R.id.edt_salesman_name -> {
                showDialog(
                    getString(R.string.select_salesman_name), data,
                    AppConstant.DialogType.SALESMAN_NAME.value
                )
            }
            R.id.edt_sales_route -> {
                if (!ValidationHelper.validateBlank(
                        mBinding.edtSalesmanName,
                        mBinding.inputSalesmanName,
                        getString(R.string.select_salesman_name)
                    )
                ) {
                    mActivity.showToast(getString(R.string.select_salesman_name))
                } else if (!salesRouteList.isNullOrEmpty())
                    showDialog(
                        getString(R.string.select_sales_route),
                        salesRouteList as ArrayList<String>,
                        AppConstant.DialogType.SALES_ROUTE.value
                    )
            }
            R.id.edt_delivery_boy -> {
                showDialog(
                    getString(R.string.select_delivery_boy), data,
                    AppConstant.DialogType.DELIVERY_BOY.value
                )
            }
            R.id.edt_delivery_route -> {
                if (!ValidationHelper.validateBlank(
                        mBinding.edtDeliveryBoy,
                        mBinding.inputDeliveryBoy,
                        getString(R.string.select_delivery_boy)
                    )
                ) {
                    mActivity.showToast(getString(R.string.select_delivery_boy))
                } else if (!deliveryRouteList.isNullOrEmpty())
                    showDialog(
                        getString(R.string.select_delivery_route),
                        deliveryRouteList as ArrayList<String>,
                        AppConstant.DialogType.DELIVERY_ROUTE.value
                    )
            }
            R.id.edt_category -> {
                showDialog(
                    getString(R.string.select_cat), data,
                    AppConstant.DialogType.CATEGORY.value
                )
            }
            R.id.edt_channel -> {
                if (!ValidationHelper.validateBlank(
                        mBinding.edtCategory,
                        mBinding.inputCategory,
                        getString(R.string.select_cat)
                    )
                ) {
                    mActivity.showToast(getString(R.string.select_cat))
                } else {
                    showDialog(
                        getString(R.string.select_channel),
                        data,
                        AppConstant.DialogType.CHANNEL.value
                    )
                }
            }
            R.id.edt_group -> {
                if (!ValidationHelper.validateBlank(
                        mBinding.edtChannel,
                        mBinding.inputChannel,
                        getString(R.string.select_channel)
                    )
                ) {
                    mActivity.showToast(getString(R.string.select_channel))
                } else {
                    showDialog(
                        getString(R.string.select_group),
                        data,
                        AppConstant.DialogType.GROUP.value
                    )
                }
            }
            R.id.edt_classes -> {
                showDialog(
                    getString(R.string.select_class),
                    data,
                    AppConstant.DialogType.CLASS.value
                )
            }

        }
    }

    private fun validate(): Boolean {
        var isValid = true
        if (mBinding.switchIsCreditLimit.isChecked && !ValidationHelper.validateBlank(
                mBinding.edtCreditLimit,
                mBinding.inputCreditLimit,
                getString(R.string.enter_credit_limit)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtCreditCash,
                mBinding.inputCreditSales,
                getString(R.string.select_credit_cash_van)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtSalesmanName,
                mBinding.inputSalesmanName,
                getString(R.string.select_salesman_name)
            )
        ) isValid = false
        if (!ValidationHelper.cashDiscountPer(
                mBinding.edtCashDiscountPercent,
                mBinding.inputCashDiscountPercent,
            )
        ) isValid = false
        mBinding.edtCashDiscountPercent.text.toString()
        if (!ValidationHelper.validateBlank(
                mBinding.edtSalesRoute,
                mBinding.inputSalesRoute,
                getString(R.string.select_sales_route)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtDeliveryBoy,
                mBinding.inputDeliveryBoy,
                getString(R.string.select_delivery_boy)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtDeliveryRoute,
                mBinding.inputDeliveryRoute,
                getString(R.string.select_delivery_route)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtCategory,
                mBinding.inputCategory,
                getString(R.string.select_cat)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtChannel,
                mBinding.inputChannel,
                getString(R.string.select_channel)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtGroup,
                mBinding.inputGroup,
                getString(R.string.select_group)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtClasses,
                mBinding.inputClasses,
                getString(R.string.select_class)
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
//      AppConstant.DialogType.PARENT.value -> {
//        mBinding.edtParent.setText(item)
////        townList= null
////        dependentPicklist(ApiConstant.DependentPicklistType.DISTRICT.value, mBinding.edtState.text.toString())
//      }
            AppConstant.DialogType.CREDIT_SALES.value -> {
                mBinding.edtCreditCash.setText(item)
//        dependentPicklist(ApiConstant.DependentPicklistType.TOWN.value, mBinding.edtDistrict.text.toString())
            }
            AppConstant.DialogType.SALESMAN_NAME.value -> {
                mBinding.edtSalesmanName.setText(item)
            }
            AppConstant.DialogType.SALES_ROUTE.value -> {
                mBinding.edtSalesRoute.setText(item)
            }
            AppConstant.DialogType.DELIVERY_BOY.value -> {
                mBinding.edtDeliveryBoy.setText(item)
                mBinding.edtDeliveryRoute.setText("")
            }
            AppConstant.DialogType.DELIVERY_ROUTE.value -> {
                mBinding.edtDeliveryRoute.setText(item)
            }
            AppConstant.DialogType.CATEGORY.value -> {
                mBinding.edtCategory.setText(item)
                mBinding.edtChannel.setText("")
                mBinding.edtGroup.setText("")
            }
            AppConstant.DialogType.CHANNEL.value -> {
                mBinding.edtChannel.setText(item)
                mBinding.edtGroup.setText("")
            }
            AppConstant.DialogType.GROUP.value -> {
                mBinding.edtGroup.setText(item)
            }
            AppConstant.DialogType.CLASS.value -> {
                mBinding.edtClasses.setText(item)
            }
        }
    }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            KycFragment.TAG -> frm = KycFragment.getInstance(tag, tag)
//      ZoomImageFrm.TAG -> frm = ZoomImageFrm.getInstance(bundle)
        }
        if (frm != null) navigateFragment(R.id.container, frm, true)
    }
}