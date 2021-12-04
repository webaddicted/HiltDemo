package com.webaddicted.newhiltproject.view.dashboard

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmDaySummaryBinding
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.view.splash.UserTypeFragment
import com.webaddicted.newhiltproject.viewmodel.HomeViewModel

class DaySummaryFragment : BaseFragment(R.layout.frm_day_summary) {
    private lateinit var mBinding: FrmDaySummaryBinding
    private val homeViewModel: HomeViewModel by viewModels()

    companion object {
        val TAG = DaySummaryFragment::class.qualifiedName
        const val BEAT_DATA = "Beat Data"
        fun getInstance(): DaySummaryFragment {
            val bundle = Bundle()
//      bundle.putSerializable(BEAT_DATA, beatRespo)
            val fragment = DaySummaryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmDaySummaryBinding
//    beatData = arguments?.getSerializable(VisitDetailsFragment.BEAT_DATA) as BeatRespo
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.day_summary))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        daySummary()
    }

    private fun daySummary() {
//            homeViewModel.getDaySummary("")
//            homeViewModel.daySummaryRespo.observe(this, {
//                  handleApiRespo(
//                    it,
//                    mBinding.loadingTyreIv
//                  ) { isFailure: Boolean, result: ApiResponse<CommonRespo<DaySummaryRespo>>? ->
//                    if (isFailure) daySummary()
//                    else {
//                      setUpData(result?.data?.respDetails)
//                    }
//                  }
//                })
        setUpData()
    }

    fun setUpData() {
        mBinding.cardView.visible()
        mBinding.includeProdIndex.txtSaleTarHdr.text = getString(R.string.productive_index)
        mBinding.includeOrderValue.txtSaleTarHdr.text = getString(R.string.order_value)
        mBinding.includeNoOfLine.txtSaleTarHdr.text = getString(R.string.no_of_lines)
        mBinding.includeCollectionTotal.txtSaleTarHdr.text = getString(R.string.collection_total)
        mBinding.includeCashTotal.txtSaleTarHdr.text = getString(R.string.cash_total)
        mBinding.includeChequeTotal.txtSaleTarHdr.text = getString(R.string.cheque_total)
        mBinding.includeUnbillRetailer.txtSaleTarHdr.text = getString(R.string.unbilled_retailer)
        mBinding.includeOrderSubmitted.txtSaleTarHdr.text = getString(R.string.order_submitted)

        mBinding.includeProdIndex.txtSaleTar.text = "250"
        mBinding.includeOrderValue.txtSaleTar.text = "750"
        mBinding.includeNoOfLine.txtSaleTar.text = "50"
        mBinding.includeCollectionTotal.txtSaleTar.text = "10"
        mBinding.includeCashTotal.txtSaleTar.text = "2"
        mBinding.includeChequeTotal.txtSaleTar.text = "20"
        mBinding.includeUnbillRetailer.txtSaleTar.text = "25"
        mBinding.includeOrderSubmitted.txtSaleTar.text = "33"
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