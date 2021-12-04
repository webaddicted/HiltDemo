package com.webaddicted.newhiltproject.view.pricelist

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmPriceListBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.showToast
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.dialog.PickerDialog
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.view.splash.UserTypeFragment

class PriceVisibilityFragment : BaseFragment(R.layout.frm_price_list), PickerDialog.ClickListener {
    private lateinit var mBinding: FrmPriceListBinding
    private var priceAdapter: PriceVisiblityAdapter? = null
    private var categoryList: List<String>? = null
    private var skuList: ArrayList<String>? = null
    private var prodList: ArrayList<String>? = null

    private var tabTitle: List<String>? = null
    private var showPostTax: Boolean = false

    companion object {
        val TAG = PriceVisibilityFragment::class.qualifiedName
        const val BEAT_DATA = "Beat Data"
        fun getInstance(bundle: Bundle): PriceVisibilityFragment {
            val fragment = PriceVisibilityFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmPriceListBinding
//        beatData = arguments?.getSerializable(VisitDetailsFragment.BEAT_DATA) as BeatRespo
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.price_list))
        (mActivity as HomeActivity).setActionBarBackIcon(false)
        (mActivity as HomeActivity).lockDrawer(false)
        categoryList =
            AppConstant.OrderCategory.values().map { it.name } as java.util.ArrayList<String>
        skuList = AppConstant.OrderCategory.values().map { it.name } as java.util.ArrayList<String>
        prodList = AppConstant.OrderCategory.values().map { it.name } as java.util.ArrayList<String>

        setUi()
        setViewPager()
        getCategoryData()
        clickListener()
    }


    private fun setUi() {
        priceAdapter = PriceVisiblityAdapter(showPostTax, prodList) { menuItem: String -> }
        mBinding.itemRv.run {
            layoutManager = LinearLayoutManager(
                mActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = priceAdapter
        }
    }

    private fun setViewPager() {
        tabTitle = AppConstant.PriceVisiblityTab.values().map { it.value }
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(tabTitle?.get(0)))
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(tabTitle?.get(1)))
    }

    private fun clickListener() {
        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                showPostTax = tab.position != 0
                setUi()
//                viewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        mBinding.edtCategory.setOnClickListener(this)
        mBinding.edtSku.setOnClickListener(this)
        mBinding.errorTv.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.edt_category -> {
                if (categoryList.isNullOrEmpty()) getCategoryData()
                else showDialog(
                    getString(R.string.select_cat),
                    categoryList as ArrayList<String>,
                    AppConstant.DialogType.PRICE_CATEGORY.value
                )
            }
            R.id.edt_sku -> {
                if (mBinding.edtCategory.text.toString()
                        .isEmpty()
                ) mActivity.showToast(getString(R.string.please_select_category))
                if (skuList.isNullOrEmpty()) skuData()
                else showDialog(
                    getString(R.string.select_sku),
                    skuList as ArrayList<String>,
                    AppConstant.DialogType.SKU.value
                )
            }
            R.id.errorTv -> {
                getCategoryData()
            }
        }
    }

    private fun getCategoryData() {
        mBinding.edtCategory.setText("")
        mBinding.edtSku.setText("")
//        subDealerViewModel.priceCategory()
//        subDealerViewModel.priceCategoryRespo.observe(this, {
//            handleApiRespo(
//                it,
//                mBinding.loadingTyreIv
//            ) { isFailure: Boolean, result: ApiResponse<CommonRespo<PriceCategoryRespo>>? ->
//                if (isFailure) getCategoryData()
//                else {
//                    if (result?.data?.isSuccess == true && result.data.respDetails !=null) {
//                        categoryList = result.data.respDetails.categoryList
//                    } else it.data?.strMessage?.let { it1 -> DialogUtils.showDialog(mActivity, it1) }
//                }
//            }
//        })
    }

    private fun skuData() {
//        subDealerViewModel.getSKUList(mBinding.edtCategory.text.toString(), mBinding.edtSku.text.toString())
//        subDealerViewModel.skuRespo.observe(this, {
//            handleApiRespo(
//                it,
//                mBinding.loadingTyreIv
//            ) { isFailure: Boolean, result: ApiResponse<CommonListRespo<String>>? ->
//                if (isFailure) getCategoryData()
//                else {
//                    if (result?.data?.isSuccess == true && result.data.respDetails !=null) {
//                        skuList = result.data.respDetails
//                    } else it.data?.strMessage?.let { it1 -> DialogUtils.showDialog(mActivity, it1) }
//                }
//            }
//        })
    }

    private fun productList() {
//        val priceList = PriceListReq(mBinding.edtCategory.text.toString(), mBinding.edtSku.text.toString())
//        subDealerViewModel.priceProductList(priceList)
//        subDealerViewModel.priceProdListRespo.observe(this, {
//            handleApiRespo(
//                it,
//                mBinding.loadingTyreIv
//            ) { isFailure: Boolean, result: ApiResponse<CommonListRespo<PriceProductListRespo>>? ->
//                if (isFailure) getCategoryData()
//                else {
//                    if (result?.data?.isSuccess == true && result.data.respDetails !=null) {
//                        prodList = result.data.respDetails
//                        setUi()
//                    } else it.data?.strMessage?.let { it1 -> DialogUtils.showDialog(mActivity, it1) }
//                }
//            }
//        })
    }


    private fun showDialog(title: String, list: ArrayList<String>, reqCode: Int) {
        val dialog = PickerDialog.dialog(title, list)
        dialog.setTargetFragment(this, reqCode)
        dialog.show(mActivity.supportFragmentManager, "PickerDialog")
    }

    override fun itemClick(item: String, targetRequestCode: Int) {
        when (targetRequestCode) {
            AppConstant.DialogType.PRICE_CATEGORY.value -> {
                mBinding.edtCategory.setText(item)
                mBinding.edtSku.setText("")
                skuData()
            }
            AppConstant.DialogType.SKU.value -> {
                mBinding.edtSku.setText(item)
            }
        }
        productList()
    }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            UserTypeFragment.TAG -> frm = UserTypeFragment.getInstance(Bundle())
//            ZoomImageFrm.TAG -> frm = ZoomImageFrm.getInstance(bundle)
        }
        if (frm != null) navigateFragment(R.id.container, frm, false)
    }
}