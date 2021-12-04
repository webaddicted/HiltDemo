package com.webaddicted.newhiltproject.view.dashboard

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmRetailerCreditBinding
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.view.splash.UserTypeFragment
import com.webaddicted.newhiltproject.viewmodel.HomeViewModel

class RetailerCreditFragment : BaseFragment(R.layout.frm_retailer_credit) {
    private var creditList: ArrayList<String>? = null
    private var retailerCreditAdapter: RetailerCreditAdapter? = null
    private lateinit var mBinding: FrmRetailerCreditBinding
    private val homeViewModel: HomeViewModel by viewModels()

    companion object {
        val TAG = RetailerCreditFragment::class.qualifiedName
        const val BEAT_DATA = "Beat Data"
        fun getInstance(): RetailerCreditFragment {
            val bundle = Bundle()
//      bundle.putSerializable(BEAT_DATA, beatRespo)
            val fragment = RetailerCreditFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmRetailerCreditBinding
//    beatData = arguments?.getSerializable(VisitDetailsFragment.BEAT_DATA) as BeatRespo
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.retailer_credit))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        setUi()
        clickListener()
        retailerCredit()
        creditList = AppConstant.VisitDetailsItem.values().map { it.value } as ArrayList<String>
        setAdapter()
    }

    private fun setUi() {
    }

    private fun clickListener() {
    }

    private fun retailerCredit() {
//            homeViewModel.getRetailerCredit("")
//            homeViewModel.retailerCreditRespo.observe(this, {
//                  handleApiRespo(
//                    it,
//                    mBinding.loadingTyreIv
//                  ) { isFailure: Boolean, result: ApiResponse<CommonListRespo<RetailerCreditRespo>>? ->
//                    if (isFailure) retailerCredit()
//                    else {
//                      creditList = result?.data?.respDetails
//                      setAdapter()
//                    }
//                  }
//                })
    }

    private fun setAdapter() {
        retailerCreditAdapter = RetailerCreditAdapter(creditList)
        mBinding.itemRv.run {
            layoutManager = LinearLayoutManager(
                mActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = retailerCreditAdapter
        }
    }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            UserTypeFragment.TAG -> frm = UserTypeFragment.getInstance(Bundle())
//          ZoomImageFrm.TAG -> frm = ZoomImageFrm.getInstance(bundle)
        }
        if (frm != null) navigateFragment(R.id.container, frm, false)
    }
}
