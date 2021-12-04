package com.webaddicted.newhiltproject.view.dashboard

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmMtdDashboardBinding
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.view.splash.UserTypeFragment
import com.webaddicted.newhiltproject.viewmodel.HomeViewModel

class MTDDashboardFragment : BaseFragment(R.layout.frm_mtd_dashboard) {
    private var spinnerAdapter: ArrayAdapter<String>? = null
    private var data: List<String>? = null
    private lateinit var mBinding: FrmMtdDashboardBinding
    private val homeViewModel: HomeViewModel by viewModels()

    companion object {
        val TAG = MTDDashboardFragment::class.qualifiedName
        const val BEAT_DATA = "Beat Data"
        fun getInstance(): MTDDashboardFragment {
            val bundle = Bundle()
//      bundle.putSerializable(BEAT_DATA, beatRespo)
            val fragment = MTDDashboardFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmMtdDashboardBinding
//    beatData = arguments?.getSerializable(VisitDetailsFragment.BEAT_DATA) as BeatRespo
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.mtd_dashboard))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        clickListener()
        getMTDDashboard()
        getSalesmanDashboard()
        setUpData()
        setUpSalesManData()
    }

    private fun clickListener() {
        data = AppConstant.SelectableLanguage.values().map { it.value }
        spinnerAdapter = ArrayAdapter(
            mActivity,
            R.layout.row_spinner_txt,
            data!!
        )
        spinnerAdapter?.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        mBinding.routesSpinner.adapter = spinnerAdapter
    }


    private fun getMTDDashboard() {
//            homeViewModel.getMtdDashboard("")
//            homeViewModel.mtdDashboardRespo.observe(this, {
//                  handleApiRespo(
//                    it,
//                    mBinding.loadingTyreIv
//                  ) { isFailure: Boolean, result: ApiResponse<CommonRespo<MTDDashboardRespo>>? ->
//                    if (isFailure) getMTDDashboard()
//                    else {
//                      setUpData(result?.data?.respDetails)
//                    }
//                  }
//                })
    }


    fun setUpData() {
        mBinding.cardView.visible()
        mBinding.includeZeroBill.txtSaleTarHdr.text = getString(R.string.zero_billed_retailers)
        mBinding.includeSaleVal.txtSaleTarHdr.text = getString(R.string.sales_value)
        mBinding.includeBillProdCount.txtSaleTarHdr.text = getString(R.string.bill_product_count)
        mBinding.includeProdCall.txtSaleTarHdr.text = getString(R.string.productive_call)
        mBinding.includeSalesCall.txtSaleTarHdr.text = getString(R.string.sales_productive_call)
        mBinding.includeLpc.txtSaleTarHdr.text = getString(R.string.lpc)
        mBinding.includeLpd.txtSaleTarHdr.text = getString(R.string.lpd)
        mBinding.includeLpm.txtSaleTarHdr.text = getString(R.string.lpm)
        mBinding.includeSalesLpd.txtSaleTarHdr.text = getString(R.string.salesman_lpd)
        mBinding.includeSalesTarget.txtSaleTarHdr.text = getString(R.string.sales_target)

        mBinding.includeZeroBill.txtSaleTar.text =
            ("776" ?: "--").toString()
        mBinding.includeSaleVal.txtSaleTar.text = ("788" ?: "--").toString()
        mBinding.includeBillProdCount.txtSaleTar.text =
            ("777" ?: "--").toString()
        mBinding.includeProdCall.txtSaleTar.text =
            ("33" ?: "--").toString()
        mBinding.includeSalesCall.txtSaleTar.text =
            ("11" ?: "--").toString()
        mBinding.includeLpc.txtSaleTar.text =
            "--"//(respDetails?.salesproductiveCall?: "--").toString()


    }

    private fun getSalesmanDashboard() {
//            homeViewModel.salesmanDashboard("")
//            homeViewModel.salesmanDashboardRespo.observe(this, {
//                  handleApiRespo(
//                    it,
//                    mBinding.loadingTyreIv
//                  ) { isFailure: Boolean, result: ApiResponse<CommonRespo<SalesmanDashboardRespo>>? ->
//                    if (isFailure) getMTDDashboard()
//                    else {
//                      setUpSalesManData(result?.data?.respDetails)
//                    }
//                  }
//                })
    }

    private fun setUpSalesManData() {
//            if (respDetails == null) {
//                  mActivity.showToast(getString(R.string.no_records_found))
//                  mBinding.saleCardView.gone()
//                  return
//                }
        mBinding.saleCardView.visible()
        mBinding.includeLpd.txtSaleTar.text = "33"
        mBinding.includeLpm.txtSaleTar.text = "89"
        mBinding.includeSalesLpd.txtSaleTar.text = "10"
        mBinding.includeSalesTarget.txtSaleTar.text = "088"
    }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            UserTypeFragment.TAG -> frm = UserTypeFragment.getInstance(Bundle())
//      ZoomImageFrm.TAG -> frm = ZoomImageFrm.getInstance(bundle)
        }
        if (frm != null) navigateFragment(R.id.container, frm, false)
    }
}

